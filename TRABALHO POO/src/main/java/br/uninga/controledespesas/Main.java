package br.uninga.controledespesas;

import br.uninga.controledespesas.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();
        DespesaService despesaService = new DespesaService();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sistema de Controle de Despesas ===");
        System.out.print("Cadastrar novo usuário? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();
            usuarioService.registrarUsuario(nome, email, senha);
        }

        System.out.print("Login - Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        if (!usuarioService.login(email, senha)) return;

        int opcao;
        do {
            System.out.println("\n1 - Adicionar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Editar Despesa");
            System.out.println("4 - Excluir Despesa");
            System.out.println("5 - Total Mensal");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();
                    System.out.print("Valor: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Data (dd/mm): ");
                    String data = sc.nextLine();
                    System.out.print("Categoria: ");
                    String cat = sc.nextLine();
                    despesaService.adicionarDespesa(desc, valor, data, cat);
                }
                case 2 -> despesaService.listarDespesas();
                case 3 -> {
                    System.out.print("ID da despesa a editar: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nova descrição: ");
                    String nd = sc.nextLine();
                    System.out.print("Novo valor: ");
                    double nv = sc.nextDouble();
                    despesaService.editarDespesa(id, nd, nv);
                }
                case 4 -> {
                    System.out.print("ID da despesa a excluir: ");
                    int id = sc.nextInt();
                    despesaService.excluirDespesa(id);
                }
                case 5 -> System.out.println("Total gasto: R$ " + despesaService.calcularTotal());
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
