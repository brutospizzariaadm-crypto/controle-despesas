
package br.uninga.controledespesas.repository;

import br.uninga.controledespesas.model.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DespesaRepository {
    private final Path arquivo = Paths.get("data/despesas.txt");
    private final TipoDespesaRepository tipoRepo;

    public DespesaRepository(TipoDespesaRepository tipoRepo) {
        this.tipoRepo = tipoRepo;
        try {
            Files.createDirectories(arquivo.getParent());
            if (!Files.exists(arquivo)) Files.createFile(arquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvar(Despesa d) {
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.APPEND)) {
            bw.write(d.toStorageString());
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Despesa> listarTudo() {
        List<Despesa> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Despesa d = parseLinha(linha);
                if (d != null) lista.add(d);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    private Despesa parseLinha(String linha) {
        String[] p = linha.split("\|", -1);
        if (p.length < 10) return null;
        try {
            int id = Integer.parseInt(p[0]);
            String className = p[1];
            String descricao = p[2];
            double valor = Double.parseDouble(p[3]);
            LocalDate venc = LocalDate.parse(p[4]);
            String tipoNome = p[5];
            boolean pago = Boolean.parseBoolean(p[6]);
            double valorPago = p[7].isEmpty() ? 0.0 : Double.parseDouble(p[7]);
            String dataPagamento = p[8].isEmpty() ? null : p[8];
            String priorityStr = p[9].isEmpty() ? null : p[9];
            String extra = p.length > 10 ? p[10] : "";

            TipoDespesa tipo = tipoRepo.buscarPorNome(tipoNome);
            if (tipo == null) tipo = new TipoDespesa(tipoNome);

            Priority prio = priorityStr == null ? null : Priority.valueOf(priorityStr);

            switch (className) {
                case "Transporte":
                    return new Transporte(id, descricao, valor, venc, tipo, pago, valorPago, dataPagamento, extra, prio);
                case "Eventual":
                    return new Eventual(id, descricao, valor, venc, tipo, pago, valorPago, dataPagamento, prio);
                case "Superfluo":
                    return new Superfluo(id, descricao, valor, venc, tipo, pago, valorPago, dataPagamento, prio);
                default:
                    return new Eventual(id, descricao, valor, venc, tipo, pago, valorPago, dataPagamento, prio);
            }
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha: " + linha + " -> " + e.getMessage());
            return null;
        }
    }

    public Despesa buscarPorId(int id) {
        for (Despesa d : listarTudo()) if (d.getId() == id) return d;
        return null;
    }

    public List<Despesa> listarPendentes() {
        return listarTudo().stream().filter(d -> !d.isPago()).collect(Collectors.toList());
    }

    public List<Despesa> listarPorPago(boolean pago) {
        return listarTudo().stream().filter(d -> d.isPago() == pago).collect(Collectors.toList());
    }

    public List<Despesa> listarPorPrioridade(Priority p) {
        return listarTudo().stream().filter(d -> d.getPriority() == p).collect(Collectors.toList());
    }

    public List<Despesa> listarPorPeriodoESituacao(LocalDate inicio, LocalDate fim, boolean pago) {
        return listarTudo().stream()
                .filter(d -> (d.getDataVencimento().isEqual(inicio) || d.getDataVencimento().isAfter(inicio)) &&
                             (d.getDataVencimento().isEqual(fim) || d.getDataVencimento().isBefore(fim)) &&
                             d.isPago() == pago)
                .collect(Collectors.toList());
    }

    public void atualizar(Despesa atualizado) {
        List<Despesa> todos = listarTudo();
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Despesa d : todos) {
                if (d.getId() == atualizado.getId()) bw.write(atualizado.toStorageString());
                else bw.write(d.toStorageString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(int id) {
        List<Despesa> todos = listarTudo();
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Despesa d : todos) {
                if (d.getId() != id) {
                    bw.write(d.toStorageString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
