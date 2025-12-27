package br.com.samuel.calculadorajuroscompostos.repository;

import br.com.samuel.calculadorajuroscompostos.model.Financiamento;
import javafx.collections.ObservableList;

public class FinanciamentoRepository {
    public double calcularValorTotalPago(ObservableList<Financiamento> listaFinanciamento) {
        double totalPago = 0;

        for (Financiamento fin : listaFinanciamento) {
            totalPago += fin.getParcela();
        }
        return totalPago;
    }

    public double calcularJurosTotal(ObservableList<Financiamento> listaFinanciamento) {
        double totalJuros = 0;
        for (Financiamento fin : listaFinanciamento) {
            totalJuros += fin.getJuros();
        }
        return totalJuros;
    }

}
