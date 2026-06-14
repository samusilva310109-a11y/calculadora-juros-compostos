package br.com.samuel.calculadorajuroscompostos.model;

import javafx.collections.ObservableList;

public class FinanciamentoSAC implements IFinanciamento{

    @Override
    public ObservableList<Financiamento> calcular(double valorPricipal, double taxa, int mes, ObservableList<Financiamento> listaFinanciamento) {

        double amortizacao = valorPricipal /  mes;
        double saldo = valorPricipal;

        for (int i = 1  ; i <= mes; i++) {
            double juros = saldo * taxa;
            double valorParcela = amortizacao + juros;
            saldo -= amortizacao;

            listaFinanciamento.add(new Financiamento(i , valorParcela, juros, saldo, amortizacao));
        }

        return listaFinanciamento;

    }

    public double calcularMontante(double capitalInicial, double taxa, int periodo){
        double txaPorcent = (taxa / 100);
        double montante = capitalInicial * Math.pow((1 + txaPorcent), periodo);
        return montante;
    }
}
