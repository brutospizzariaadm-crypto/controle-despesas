package br.uninga.controledespesas.model;

import java.time.LocalDate;

public class Transporte extends Despesa {
    private String meioTransporte; // atributo específico

    public Transporte(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, String meioTransporte) {
        super(descricao, valor, dataVencimento, tipo);
        this.meioTransporte = meioTransporte;
    }

    public Transporte(String descricao, double valor, TipoDespesa tipo, String meioTransporte) {
        super(descricao, valor, tipo);
        this.meioTransporte = meioTransporte;
    }

    // sobrescrita de toString
    @Override
    public String toString() {
        return "Transporte - " + super.toString() + " [meio=" + meioTransporte + "]";
    }
}
