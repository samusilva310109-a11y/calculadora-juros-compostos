package br.com.samuel.calculadorajuroscompostos.controller;

import br.com.samuel.calculadorajuroscompostos.model.Financiamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaFinanciamentoController {

    @FXML
    private TextField txfMeses;

    @FXML
    private TextField txfTaxa;

    @FXML
    private TextField txfValorPrincipal;

    @FXML
    private Label lblCustoTotal;

    @FXML
    private Label lblJuros;

    @FXML
    private Label lblValorFinanciado;

    @FXML
    private TableView<Financiamento> tableFinanciamento;

    @FXML
    private TableColumn<Financiamento, Double> colAmortizacao;

    @FXML
    private TableColumn<Financiamento, Double> colJuros;

    @FXML
    private TableColumn<Financiamento, Integer> colMes;

    @FXML
    private TableColumn<Financiamento, Double> colParcela;

    @FXML
    private TableColumn<Financiamento, Double> colSaldo;

    private ObservableList<Financiamento> listaFinanciamento =  FXCollections.observableArrayList();

    public void initialize(){
        lblJuros.setVisible(false);
        lblValorFinanciado.setVisible(false);
        lblCustoTotal.setVisible(false);

        colMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
        colParcela.setCellValueFactory(new PropertyValueFactory<>("parcela"));
        colAmortizacao.setCellValueFactory(new PropertyValueFactory<>("amortizacao"));
        colJuros.setCellValueFactory(new PropertyValueFactory<>("juros"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoDevedor"));
        tableFinanciamento.setItems(listaFinanciamento);
    }

    public void calcularSAC(){
        listaFinanciamento.clear();

        double valorPrincipal = Double.parseDouble(txfValorPrincipal.getText());
        double taxa = Double.parseDouble(txfTaxa.getText()) / 100.0;
        int valorMes = Integer.parseInt(txfMeses.getText());

        double amortizacao = valorPrincipal /  valorMes;
        double saldo = valorPrincipal;

        for (int i = 0; i <= valorMes; i++) {
            double juros = saldo * taxa;
            double valorParcela = amortizacao + juros;
            saldo -= amortizacao;

            String jurosFormatado = String.format("R$ %,.2f", Math.max(0, juros));
            String valorParcelaFormatado = String.format("R$ %,.2f", valorParcela);
            String saldoFormatado = String.format("R$ %,.2f",  Math.max(0, saldo));
            String amortizacaoFormatado = String.format("R$ %,.2f", amortizacao);



            listaFinanciamento.add(new Financiamento(valorMes, valorParcelaFormatado, jurosFormatado, saldoFormatado, amortizacaoFormatado));
        }

        tableFinanciamento.setItems(listaFinanciamento);
    }

}

