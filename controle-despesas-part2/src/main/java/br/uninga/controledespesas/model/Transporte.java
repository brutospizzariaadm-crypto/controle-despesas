
package br.uninga.controledespesas.model;

import java.time.LocalDate;

public class Transporte extends Despesa {
    private String meioTransporte;

    public Transporte(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, String meioTransporte, Priority priority) {
        super(descricao, valor, dataVencimento, tipo, priority);
        this.meioTransporte = meioTransporte;
    }

    public Transporte(int id, String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo,
                      boolean pago, double valorPago, String dataPagamento, String meioTransporte, Priority priority) {
        super(id, descricao, valor, dataVencimento, tipo, pago, valorPago, dataPagamento, priority);
        this.meioTransporte = meioTransporte;
    }

    public String getMeioTransporte() { return meioTransporte; }
    public void setMeioTransporte(String m) { this.meioTransporte = m; }

    @Override
    protected String getExtraFieldForStorage() {
        return escape(meioTransporte);
    }

    @Override
    public String toString() {
        return "Transporte - " + super.toString() + (meioTransporte != null ? " - Meio: " + meioTransporte : "");
    }
}
