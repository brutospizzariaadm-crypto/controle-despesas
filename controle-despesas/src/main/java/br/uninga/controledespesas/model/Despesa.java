package br.uninga.controledespesas.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Despesa implements Pagavel {
    private static AtomicInteger contador = new AtomicInteger(0); // atributo estático
    protected final int id;
    protected String descricao;
    protected double valor;
    protected LocalDate dataVencimento;
    protected TipoDespesa tipo;
    protected boolean pago;
    protected double valorPago;
    protected String dataPagamento;

    // Construtores sobrecarregados
    public Despesa(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        this.id = contador.incrementAndGet();
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.pago = false;
    }

    public Despesa(String descricao, double valor, TipoDespesa tipo) {
        this(descricao, valor, LocalDate.now().plusDays(30), tipo); // vencimento padrão 30 dias
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoDespesa getTipo() { return tipo; }

    // Implementação padrão da interface Pagavel
    @Override
    public void registrarPagamento(double valor, String data) {
        this.valorPago = valor;
        this.dataPagamento = data;
        this.pago = true;
    }

    @Override
    public boolean isPago() {
        return pago;
    }

    @Override
    public String toString() {
        return String.format("Despesa[id=%d, desc=%s, valor=%.2f, venc=%s, tipo=%s, pago=%s]",
                id, descricao, valor, dataVencimento, tipo.getNome(), pago);
    }
}
