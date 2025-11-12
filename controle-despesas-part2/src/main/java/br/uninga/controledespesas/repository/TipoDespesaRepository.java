
package br.uninga.controledespesas.repository;

import br.uninga.controledespesas.model.TipoDespesa;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDespesaRepository {
    private final Path arquivo = Paths.get("data/tipos_despesa.txt");

    public TipoDespesaRepository() {
        try {
            Files.createDirectories(arquivo.getParent());
            if (!Files.exists(arquivo)) Files.createFile(arquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvar(TipoDespesa tipo) {
        String linha = tipo.getNome() + ";" + (tipo.getDescricao() == null ? "" : tipo.getDescricao());
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.APPEND)) {
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TipoDespesa> listar() {
        List<TipoDespesa> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";", -1);
                if (p.length >= 1) {
                    lista.add(new TipoDespesa(p[0], p.length > 1 ? (p[1].isEmpty() ? null : p[1]) : null));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public TipoDespesa buscarPorNome(String nome) {
        for (TipoDespesa t : listar()) if (t.getNome().equalsIgnoreCase(nome)) return t;
        return null;
    }

    public void atualizar(TipoDespesa novo, String nomeAntigo) {
        List<TipoDespesa> todos = listar();
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (TipoDespesa t : todos) {
                if (t.getNome().equalsIgnoreCase(nomeAntigo)) {
                    bw.write(novo.getNome() + ";" + (novo.getDescricao() == null ? "" : novo.getDescricao()));
                } else {
                    bw.write(t.getNome() + ";" + (t.getDescricao() == null ? "" : t.getDescricao()));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(String nome) {
        List<TipoDespesa> todos = listar();
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (TipoDespesa t : todos) {
                if (!t.getNome().equalsIgnoreCase(nome)) {
                    bw.write(t.getNome() + ";" + (t.getDescricao() == null ? "" : t.getDescricao()));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
