
package br.uninga.controledespesas.model;

import java.time.LocalDate;

public class Eventual extends Despesa {
    public Eventual(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, Priority priority) {
        super(descricao, valor, dataVencimento, tipo, priority);
    }

    public Eventual(int id, String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo,
                    boolean pago, double valorPago, String dataPagamento, Priority priority) {
        super(id, descricao, valor, dataVencimento, tipo, pago, valorPago, dataPagamento, priority);
    }

    @Override
    public String toString() {
        return "Eventual - " + super.toString();
    }
}
