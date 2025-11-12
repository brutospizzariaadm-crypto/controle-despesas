
package br.uninga.controledespesas;

import br.uninga.controledespesas.model.*;
import br.uninga.controledespesas.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final TipoDespesaRepository tipoRepo = new TipoDespesaRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final DespesaRepository despesaRepo = new DespesaRepository(tipoRepo);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CONTROLE DE DESPESAS (MVP - PARTE 02) ===");
        inicializarPadrao();

        boolean running = true;
        while (running) {
            mostraMenuPrincipal();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": entrarDespesa(); break;
                case "2": anotarPagamento(); break;
                case "3": listarDespesas(); break;
                case "4": listarDespesasPorPrioridade(); break;
                case "5": gerenciarTipos(); break;
                case "6": gerenciarUsuarios(); break;
                case "7":
                    System.out.println("Saindo...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void mostraMenuPrincipal(){
        System.out.println();
        System.out.println("Menu Principal:");
        System.out.println("1 - Entrar Despesa");
        System.out.println("2 - Anotar Pagamento");
        System.out.println("3 - Listar Despesas (todas/filter pendentes/pagas)");
        System.out.println("4 - Listar Despesas por Prioridade");
        System.out.println("5 - Gerenciar Tipos de Despesa");
        System.out.println("6 - Gerenciar Usuários");
        System.out.println("7 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void inicializarPadrao() {
        if (tipoRepo.listar().isEmpty()) {
            tipoRepo.salvar(new TipoDespesa("Alimentação", "Despesas com comida"));
            tipoRepo.salvar(new TipoDespesa("Transporte", "Ônibus, moto, gasolina"));
            tipoRepo.salvar(new TipoDespesa("Lazer", "Cinema, passeios"));
        }
    }

    private static void entrarDespesa() {
        System.out.println();
        System.out.println("== Entrar Despesa ==");
        System.out.print("Descrição: ");
        String descricao = sc.nextLine().trim();
        System.out.print("Valor (ex: 123.45): ");
        double valor = lerDouble();
        System.out.print("Data de vencimento (YYYY-MM-DD) [enter = 30 dias]: ");
        String dataStr = sc.nextLine().trim();
        LocalDate venc = dataStr.isEmpty() ? LocalDate.now().plusDays(30) : LocalDate.parse(dataStr, fmt);

        System.out.println("Tipos disponíveis:");
        List<TipoDespesa> tipos = tipoRepo.listar();
        for (int i = 0; i < tipos.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, tipos.get(i).toString());
        }
        System.out.print("Escolha o tipo (número): ");
        int idx = lerIntLimite(1, tipos.size());
        TipoDespesa tipo = tipos.get(idx - 1);

        System.out.println("Prioridade: 1-BAIXA 2-MEDIA 3-ALTA");
        int p = lerIntLimite(1,3);
        Priority prio = p==1?Priority.BAIXA: p==2?Priority.MEDIA:Priority.ALTA;

        System.out.println("Categoria da despesa:");
        System.out.println("1 - Transporte");
        System.out.println("2 - Eventual");
        System.out.println("3 - Supérfluo");
        System.out.print("Escolha (1-3): ");
        int cat = lerIntLimite(1,3);

        Despesa d;
        if (cat == 1) {
            System.out.print("Meio de transporte (opcional): ");
            String meio = sc.nextLine().trim();
            d = new Transporte(descricao, valor, venc, tipo, meio, prio);
        } else if (cat == 2) {
            d = new Eventual(descricao, valor, venc, tipo, prio);
        } else {
            d = new Superfluo(descricao, valor, venc, tipo, prio);
        }

        despesaRepo.salvar(d);
        System.out.println("Despesa cadastrada: " + d);
    }

    private static void anotarPagamento() {
        System.out.println();
        System.out.println("== Anotar Pagamento ==");
        List<Despesa> pendentes = despesaRepo.listarPendentes();
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma despesa pendente.");
            return;
        }
        System.out.println("Despesas pendentes:");
        for (Despesa d : pendentes) {
            System.out.println(d.getId() + " - " + d.getDescricao() + " - Venc: " + d.getDataVencimento() + " - R$ " + d.getValor());
        }
        System.out.print("Digite o ID da despesa que recebeu pagamento: ");
        int id = lerInt();
        Despesa target = despesaRepo.buscarPorId(id);
        if (target == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }
        System.out.print("Valor pago (ex: 123.45): ");
        double pago = lerDouble();
        System.out.print("Data do pagamento (YYYY-MM-DD) [enter = hoje]: ");
        String data = sc.nextLine().trim();
        String dataPag = data.isEmpty() ? LocalDate.now().format(fmt) : data;
        target.registrarPagamento(pago, dataPag);
        despesaRepo.atualizar(target);
        System.out.println("Pagamento registrado para despesa id=" + id);
    }

    private static void listarDespesas() {
        System.out.println();
        System.out.println("== Listar Despesas ==");
        System.out.println("1 - Todas");
        System.out.println("2 - Apenas pendentes");
        System.out.println("3 - Apenas pagas");
        System.out.print("Escolha: ");
        String op = sc.nextLine().trim();
        List<Despesa> res;
        if (op.equals("2")) res = despesaRepo.listarPendentes();
        else if (op.equals("3")) res = despesaRepo.listarPorPago(true);
        else res = despesaRepo.listarTudo();

        if (res.isEmpty()) { System.out.println("Nenhuma despesa encontrada."); return; }
        for (Despesa d : res) System.out.println(d);
        submenuEditarExcluir();
    }

    private static void listarDespesasPorPrioridade() {
        System.out.println();
        System.out.println("== Listar por Prioridade ==");
        System.out.println("1 - BAIXA
2 - MEDIA
3 - ALTA");
        int p = lerIntLimite(1,3);
        Priority prio = p==1?Priority.BAIXA: p==2?Priority.MEDIA:Priority.ALTA;
        List<Despesa> res = despesaRepo.listarPorPrioridade(prio);
        if (res.isEmpty()) { System.out.println("Nenhuma despesa com essa prioridade."); return; }
        for (Despesa d : res) System.out.println(d);
    }

    private static void submenuEditarExcluir() {
        System.out.println();
        System.out.println("Submenu:");
        System.out.println("1 - Editar Despesa");
        System.out.println("2 - Excluir Despesa");
        System.out.println("3 - Voltar ao Menu Principal");
        System.out.print("Escolha: ");
        String op = sc.nextLine().trim();
        switch (op) {
            case "1": editarDespesa(); break;
            case "2": excluirDespesa(); break;
            default: System.out.println("Voltando ao menu principal.");
        }
    }

    private static void editarDespesa() {
        System.out.print("Digite o ID da despesa a editar: ");
        int id = lerInt();
        Despesa d = despesaRepo.buscarPorId(id);
        if (d == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }
        System.out.println("Editando: " + d);
        System.out.print("Nova descrição (enter para manter): ");
        String desc = sc.nextLine().trim();
        if (!desc.isEmpty()) d.setDescricao(desc);

        System.out.print("Novo valor (enter para manter): ");
        String val = sc.nextLine().trim();
        if (!val.isEmpty()) d.setValor(Double.parseDouble(val));

        System.out.print("Nova data de vencimento (YYYY-MM-DD) (enter para manter): ");
        String dv = sc.nextLine().trim();
        if (!dv.isEmpty()) d.setDataVencimento(LocalDate.parse(dv, fmt));

        System.out.print("Alterar prioridade? (s/n): ");
        String alt = sc.nextLine().trim().toLowerCase();
        if (alt.equals("s") || alt.equals("y")) {
            System.out.println("Prioridade: 1-BAIXA 2-MEDIA 3-ALTA");
            int p = lerIntLimite(1,3);
            d.setPriority(p==1?Priority.BAIXA: p==2?Priority.MEDIA:Priority.ALTA);
        }

        despesaRepo.atualizar(d);
        System.out.println("Despesa atualizada: " + d);
    }

    private static void excluirDespesa() {
        System.out.print("Digite o ID da despesa a excluir: ");
        int id = lerInt();
        Despesa d = despesaRepo.buscarPorId(id);
        if (d == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }
        System.out.print("Confirma exclusão da despesa id=" + id + " (s/n): ");
        String c = sc.nextLine().trim().toLowerCase();
        if (c.equals("s") || c.equals("y")) {
            despesaRepo.excluir(id);
            System.out.println("Despesa excluída.");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void gerenciarTipos() {
        System.out.println();
        System.out.println("== Gerenciar Tipos de Despesa ==");
        System.out.println("1 - Listar Tipos");
        System.out.println("2 - Criar Tipo");
        System.out.println("3 - Editar Tipo");
        System.out.println("4 - Excluir Tipo");
        System.out.println("5 - Voltar");
        System.out.print("Escolha: ");
        String op = sc.nextLine().trim();
        switch (op) {
            case "1":
                List<TipoDespesa> tipos = tipoRepo.listar();
                for (TipoDespesa t : tipos) System.out.println(t);
                break;
            case "2":
                System.out.print("Nome do tipo: ");
                String nome = sc.nextLine().trim();
                System.out.print("Descrição (opcional): ");
                String desc = sc.nextLine().trim();
                tipoRepo.salvar(new TipoDespesa(nome, desc.isEmpty() ? null : desc));
                System.out.println("Tipo salvo.");
                break;
            case "3":
                System.out.print("Nome do tipo a editar: ");
                String antigo = sc.nextLine().trim();
                TipoDespesa t = tipoRepo.buscarPorNome(antigo);
                if (t == null) { System.out.println("Tipo não encontrado."); break; }
                System.out.print("Novo nome (enter para manter): ");
                String novo = sc.nextLine().trim();
                if (!novo.isEmpty()) t.setNome(novo);
                System.out.print("Nova descrição (enter para manter): ");
                String nd = sc.nextLine().trim();
                if (!nd.isEmpty()) t.setDescricao(nd);
                tipoRepo.atualizar(t, antigo);
                System.out.println("Tipo atualizado.");
                break;
            case "4":
                System.out.print("Nome do tipo a excluir: ");
                String nomeExcluir = sc.nextLine().trim();
                tipoRepo.excluir(nomeExcluir);
                System.out.println("Tipo excluído (se existia).");
                break;
            default:
                System.out.println("Voltando...");
        }
    }

    private static void gerenciarUsuarios() {
        System.out.println();
        System.out.println("== Gerenciar Usuários ==");
        System.out.println("1 - Listar Usuários");
        System.out.println("2 - Criar Usuário");
        System.out.println("3 - Voltar");
        System.out.print("Escolha: ");
        String op = sc.nextLine().trim();
        switch (op) {
            case "1":
                List<Usuario> usuarios = usuarioRepo.listar();
                for (Usuario u : usuarios) System.out.println(u.getLogin());
                break;
            case "2":
                System.out.print("Login: ");
                String login = sc.nextLine().trim();
                System.out.print("Senha: ");
                String senha = sc.nextLine().trim();
                Usuario novo = new Usuario(login, senha);
                usuarioRepo.salvar(novo);
                System.out.println("Usuário salvo (senha criptografada).");
                break;
            default:
                System.out.println("Voltando...");
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s.replace(",", "."));
            } catch (Exception e) {
                System.out.print("Valor inválido. Tente novamente: ");
            }
        }
    }

    private static int lerInt() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Número inválido. Tente novamente: ");
            }
        }
    }

    private static int lerIntLimite(int min, int max) {
        while (true) {
            int v = lerInt();
            if (v >= min && v <= max) return v;
            System.out.print("Valor fora do intervalo. Tente novamente: ");
        }
    }
}
