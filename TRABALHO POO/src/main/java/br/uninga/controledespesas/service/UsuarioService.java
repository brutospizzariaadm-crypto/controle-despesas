package br.uninga.controledespesas.service;

import br.uninga.controledespesas.model.Usuario;
import java.util.HashMap;
import java.util.Map;

public class UsuarioService {
    private Map<String, Usuario> usuarios = new HashMap<>();

    public void registrarUsuario(String nome, String email, String senha) {
        usuarios.put(email, new Usuario(nome, email, senha));
        System.out.println("Usuário registrado com sucesso!");
    }

    public boolean login(String email, String senha) {
        Usuario u = usuarios.get(email);
        if (u != null && u.autenticar(senha)) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + u.getNome());
            return true;
        }
        System.out.println("E-mail ou senha incorretos!");
        return false;
    }
}
