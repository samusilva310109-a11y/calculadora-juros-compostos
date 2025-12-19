package br.com.samuel.calculadorajuroscompostos.repository;

public class CalculadoraRepository {

    public double calcularMontante(double capitalInicial, double taxa, int tempo) {
        double taxaPorCento = taxa / 100.0;
        double somaTaxa = (1.0 + taxaPorCento);
        double somaTaxaElevado = Math.pow(somaTaxa, tempo);

        double montante = capitalInicial * somaTaxaElevado;
        return montante;
    }

    public double calcularJuros(double capitalInicial, double montante) {
        double juros = montante -  capitalInicial;
        return juros;
    }
}
