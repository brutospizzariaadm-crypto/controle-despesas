
package br.uninga.controledespesas.model;

public class TipoDespesa {
    private String nome;
    private String descricao;

    public TipoDespesa(String nome) {
        this.nome = nome;
    }

    public TipoDespesa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return nome + (descricao != null ? " - " + descricao : "");
    }
}
