package br.com.samuel.calculadorajuroscompostos.model;

public class Financiamento {
    private int mes;
    private String parcela;
    private String juros;
    private String saldoDevedor;
    private String amortizacao;

    public Financiamento(int mes, String parcela, String juros, String saldoDevedor, String amortizacao) {
        this.mes = mes;
        this.parcela = parcela;
        this.juros = juros;
        this.saldoDevedor = saldoDevedor;
        this.amortizacao = amortizacao;
    }

    public int getMes() {
        return mes;
    }

    public String getParcela() {
        return parcela;
    }

    public String getJuros() {
        return juros;
    }

    public String getSaldoDevedor() {
        return saldoDevedor;
    }

    public String getAmortizacao() {
        return amortizacao;
    }

}
