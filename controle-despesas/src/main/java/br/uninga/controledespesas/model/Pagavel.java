package br.uninga.controledespesas.model;

public interface Pagavel {
    void registrarPagamento(double valor, String data);
    boolean isPago();
}
