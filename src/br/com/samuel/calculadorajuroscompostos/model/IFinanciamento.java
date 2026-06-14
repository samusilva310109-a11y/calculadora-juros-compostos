package br.com.samuel.calculadorajuroscompostos.model;

import javafx.collections.ObservableList;

public interface IFinanciamento {
    public abstract ObservableList<Financiamento> calcular(double valorPricipal, double taxa, int mes, ObservableList<Financiamento> listaFinanciamento);
}
