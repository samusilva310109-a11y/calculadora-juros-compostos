package br.com.samuel.calculadorajuroscompostos.controller;

import br.com.samuel.calculadorajuroscompostos.model.Financiamento;
import br.com.samuel.calculadorajuroscompostos.repository.FinanciamentoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.util.Locale;

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
        colParcela.setCellFactory(tableColumn ->  new TableCell<Financiamento, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                }else {
                    Locale locale = new Locale("pt", "BR");
                    setText(NumberFormat.getCurrencyInstance(locale).format(item));
                }
            }
        });

        colAmortizacao.setCellValueFactory(new PropertyValueFactory<>("amortizacao"));
        colAmortizacao.setCellFactory(tableColumn ->  new TableCell<Financiamento, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                }else {
                    Locale locale = new Locale("pt", "BR");
                    setText(NumberFormat.getCurrencyInstance(locale).format(item));
                }
            }
        });

        colJuros.setCellValueFactory(new PropertyValueFactory<>("juros"));
        colJuros.setCellFactory(tc -> new TableCell<Financiamento, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    Locale locale = new Locale("pt", "BR");
                    setText(NumberFormat.getCurrencyInstance(locale).format(item));
                }
            }
        });

        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoDevedor"));
        colSaldo.setCellFactory(tableColumn -> new TableCell<Financiamento, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    Locale locale = new Locale("pt", "BR");
                    setText(NumberFormat.getCurrencyInstance(locale).format(item));
                }
            }
        });

        tableFinanciamento.setItems(listaFinanciamento);
    }

    public void calcularSAC(){
        listaFinanciamento.clear();

        double valorPrincipal = Double.parseDouble(txfValorPrincipal.getText());
        double taxa = Double.parseDouble(txfTaxa.getText()) / 100.0;
        int valorMes = Integer.parseInt(txfMeses.getText());

        double amortizacao = valorPrincipal /  valorMes;
        double saldo = valorPrincipal;


        for (int i = 1  ; i <= valorMes; i++) {

            double juros = saldo * taxa;
            double valorParcela = amortizacao + juros;
            saldo -= amortizacao;

            listaFinanciamento.add(new Financiamento(i , valorParcela, juros, saldo, amortizacao));
        }

        tableFinanciamento.setItems(listaFinanciamento);
        mostarResultados();
    }

    public void mostarResultados(){
        FinanciamentoRepository fr = new FinanciamentoRepository();
        double custoTotal = fr.calcularValorTotalPago(listaFinanciamento);
        double jurosTotal = fr.calcularJurosTotal(listaFinanciamento);
        double valorPrincipal = Double.parseDouble(txfValorPrincipal.getText());

        String custoTotalFormatado = String.format("R$ %,.2f", custoTotal);
        String jurosTotalFormatado = String.format("R$ %,.2f", jurosTotal);
        String valorPrincipalFormatado = String.format("R$ %,.2f", valorPrincipal);

        lblCustoTotal.setVisible(true);
        lblCustoTotal.setText(custoTotalFormatado);

        lblJuros.setVisible(true);
        lblJuros.setText(jurosTotalFormatado);

        lblValorFinanciado.setVisible(true);
        lblValorFinanciado.setText(valorPrincipalFormatado);
    }

    public void limpar(){
        lblCustoTotal.setVisible(false);
        lblJuros.setVisible(false);
        lblValorFinanciado.setVisible(false);

        listaFinanciamento.clear();
        txfValorPrincipal.setText("");
        txfTaxa.setText("");
        txfMeses.setText("");

        txfValorPrincipal.requestFocus();
    }

}

