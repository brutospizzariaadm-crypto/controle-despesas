package br.uninga.controledespesas.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
    private String nome;
    private String email;
    private String senhaHash;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = criptografarSenha(senha);
    }

    private String criptografarSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro na criptografia de senha!");
        }
    }

    public boolean autenticar(String senha) {
        return senhaHash.equals(criptografarSenha(senha));
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
}
