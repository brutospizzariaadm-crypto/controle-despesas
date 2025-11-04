package br.uninga.controledespesas.model;

import java.time.LocalDate;

public class Eventual extends Despesa {
    public Eventual(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        super(descricao, valor, dataVencimento, tipo);
    }

    public Eventual(String descricao, double valor, TipoDespesa tipo) {
        super(descricao, valor, tipo);
    }

    @Override
    public String toString() {
        return "Eventual - " + super.toString();
    }
}
