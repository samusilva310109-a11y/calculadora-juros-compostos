package br.com.samuel.calculadorajuroscompostos.model;

public class Financiamento {
    private int mes;
    private double parcela;
    private double juros;
    private double saldoDevedor;
    private double amortizacao;

    public Financiamento(int mes, double parcela, double juros, double saldoDevedor, double amortizacao) {
        this.mes = mes;
        this.parcela = parcela;
        this.juros = juros;
        this.saldoDevedor = saldoDevedor;
        this.amortizacao = amortizacao;
    }

    public int getMes() {
        return mes;
    }

    public double getParcela() {
        return parcela;
    }

    public double getJuros() {
        return juros;
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public double getAmortizacao() {
        return amortizacao;
    }



}
