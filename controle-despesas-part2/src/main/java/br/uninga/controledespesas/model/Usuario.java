
package br.uninga.controledespesas.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
    private String login;
    private String senhaHash;

    public Usuario(String login, String senhaPlain) {
        this.login = login;
        this.senhaHash = hashSenha(senhaPlain);
    }

    public Usuario(String login, String senhaHash, boolean isHash) {
        this.login = login;
        this.senhaHash = senhaHash;
    }

    public String getLogin() { return login; }
    public String getSenhaHash() { return senhaHash; }

    public static String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 não disponível", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}
