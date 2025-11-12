
package br.uninga.controledespesas.model;

import java.time.LocalDate;

public class Superfluo extends Despesa {
    public Superfluo(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, Priority priority) {
        super(descricao, valor, dataVencimento, tipo, priority);
    }

    public Superfluo(int id, String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo,
                     boolean pago, double valorPago, String dataPagamento, Priority priority) {
        super(id, descricao, valor, dataVencimento, tipo, pago, valorPago, dataPagamento, priority);
    }

    @Override
    public String toString() {
        return "Supérfluo - " + super.toString();
    }
}
