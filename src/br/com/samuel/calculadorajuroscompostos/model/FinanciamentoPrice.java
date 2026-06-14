package br.com.samuel.calculadorajuroscompostos.model;

import javafx.collections.ObservableList;

public class FinanciamentoPrice implements IFinanciamento{

    @Override
    public ObservableList<Financiamento> calcular(double valorPricipal, double taxa, int mes, ObservableList<Financiamento> listaFinanciamento) {
        double saldoDevedor = valorPricipal;


        double parteA= (Math.pow((1 + taxa), mes) * taxa);
        double parteB = (Math.pow((1 + taxa), mes) - 1);
        double prestacao = valorPricipal *  parteA / parteB;

        for(int i = 1; i <= mes; i++){
            double juros =(saldoDevedor * taxa);
            double amortizacao = prestacao - juros;
            saldoDevedor -= amortizacao;

            listaFinanciamento.add(new Financiamento(i, prestacao, juros, saldoDevedor, amortizacao));
        }

        return listaFinanciamento;
    }
}
