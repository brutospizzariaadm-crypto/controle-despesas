
package br.uninga.controledespesas.repository;

import br.uninga.controledespesas.model.Usuario;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private final Path arquivo = Paths.get("data/usuarios.txt");

    public UsuarioRepository() {
        try {
            Files.createDirectories(arquivo.getParent());
            if (!Files.exists(arquivo)) Files.createFile(arquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvar(Usuario u) {
        String linha = u.getLogin() + ";" + u.getSenhaHash();
        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardOpenOption.APPEND)) {
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(";", -1);
                if (parts.length >= 2) {
                    lista.add(new Usuario(parts[0], parts[1], true));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}
