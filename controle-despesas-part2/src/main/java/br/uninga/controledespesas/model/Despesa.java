
package br.uninga.controledespesas.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Despesa implements Pagavel {
    private static AtomicInteger contador = new AtomicInteger(0);

    protected final int id;
    protected String descricao;
    protected double valor;
    protected LocalDate dataVencimento;
    protected TipoDespesa tipo;
    protected boolean pago;
    protected double valorPago;
    protected String dataPagamento;
    protected Priority priority;

    public Despesa(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, Priority priority) {
        this.id = contador.incrementAndGet();
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.pago = false;
        this.priority = priority;
    }

    public Despesa(int id, String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo,
                   boolean pago, double valorPago, String dataPagamento, Priority priority) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.pago = pago;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.priority = priority;
        contador.updateAndGet(curr -> Math.max(curr, id));
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public java.time.LocalDate getDataVencimento() { return dataVencimento; }
    public TipoDespesa getTipo() { return tipo; }
    public double getValorPago() { return valorPago; }
    public String getDataPagamento() { return dataPagamento; }
    public Priority getPriority() { return priority; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) { this.valor = valor; }
    public void setDataVencimento(java.time.LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public void setTipo(TipoDespesa tipo) { this.tipo = tipo; }
    public void setPriority(Priority p) { this.priority = p; }

    @Override
    public void registrarPagamento(double valor, String data) {
        this.valorPago = valor;
        this.dataPagamento = data;
        this.pago = true;
    }

    @Override
    public boolean isPago() { return pago; }

    public String toStorageString() {
        // formato: id|class|descricao|valor|dataVenc|tipoNome|pago|valorPago|dataPagamento|priority|extra
        return String.join("|",
                String.valueOf(id),
                this.getClass().getSimpleName(),
                escape(descricao),
                String.valueOf(valor),
                dataVencimento.toString(),
                escape(tipo.getNome()),
                String.valueOf(pago),
                String.valueOf(valorPago),
                (dataPagamento == null ? "" : dataPagamento),
                (priority == null ? "" : priority.name()),
                getExtraFieldForStorage()
        );
    }

    protected String getExtraFieldForStorage() { return ""; }

    public static String escape(String s) {
        return s == null ? "" : s.replace("|", "/");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - R$ %.2f - Venc: %s - Tipo: %s - Prioridade: %s - Pago: %s",
                id, descricao, valor, dataVencimento, tipo.getNome(), (priority==null?"N/A":priority.name()), pago ? "Sim" : "Não");
    }
}
