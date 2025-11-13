package br.uninga.controledespesas.model;

public class Despesa {
    private int id;
    private String descricao;
    private double valor;
    private String data;
    private String categoria;

    public Despesa(int id, String descricao, double valor, String data, String categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public String getData() { return data; }
    public String getCategoria() { return categoria; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) { this.valor = valor; }
    public void setData(String data) { this.data = data; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format("[%d] %s - R$ %.2f (%s) em %s", id, descricao, valor, categoria, data);
    }
}
