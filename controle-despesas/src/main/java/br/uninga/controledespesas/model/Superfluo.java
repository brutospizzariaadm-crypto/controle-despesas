package br.uninga.controledespesas.model;

import java.time.LocalDate;

public class Superfluo extends Despesa {
    public Superfluo(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        super(descricao, valor, dataVencimento, tipo);
    }

    public Superfluo(String descricao, double valor, TipoDespesa tipo) {
        super(descricao, valor, tipo);
    }

    @Override
    public String toString() {
        return "Supérfluo - " + super.toString();
    }
}
