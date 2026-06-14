package br.com.samuel.calculadorajuroscompostos.controller;

import br.com.samuel.calculadorajuroscompostos.model.Financiamento;
import br.com.samuel.calculadorajuroscompostos.model.FinanciamentoPrice;
import br.com.samuel.calculadorajuroscompostos.model.FinanciamentoSAC;
import br.com.samuel.calculadorajuroscompostos.model.IFinanciamento;
import br.com.samuel.calculadorajuroscompostos.repository.FinanciamentoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class TelaFinanciamentoController {

    @FXML
    private ChoiceBox<String> AmortizacaoChoiceBox;

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

        //-----|Itens de AmortizacaoChoiceBox|-----
        String[] tiposAmortizacao = {"SAC", "Price"};

        AmortizacaoChoiceBox.getItems().addAll(tiposAmortizacao);


        //-----|Resumo Financeiro Sumário|-------
        lblJuros.setVisible(false);
        lblValorFinanciado.setVisible(false);
        lblCustoTotal.setVisible(false);



        //--------|Tabela de financiamento|--------
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

    //Função responsável por verificar see os valores de input estão vazios
    public void verificarVazio(){
        if (txfValorPrincipal.getText().isEmpty() || txfTaxa.getText().isEmpty() || txfMeses.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos vazios");
            alert.setHeaderText("Preencha todos os campos");
            alert.show();
        }else {
            calcular();
        }
    }

    //Função responsável por realizar o cálculo do financiamento em determinado tipo de amortização
    private void calcular(){

        listaFinanciamento.clear();
        double valorPrincipal = Double.parseDouble(txfValorPrincipal.getText());
        double taxa = Double.parseDouble(txfTaxa.getText()) / 100.0;
        int valorMes = Integer.parseInt(txfMeses.getText());

        boolean isZero = isZero(valorPrincipal, taxa, valorMes);

        if(isZero){//Verifica se os valores dos TextLabels são igual a zero
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dados inválidos");
            alert.setHeaderText("Os valores de entrada não podem ser menor ou igual a zero.");
            alert.show();

            limpar();
        }else{

            String tipoAmortizacao = AmortizacaoChoiceBox.getValue();

            if (tipoAmortizacao.equals("SAC")){
                IFinanciamento financiamento = new FinanciamentoSAC();
                ObservableList<Financiamento> finSAC = financiamento.calcular(valorPrincipal, taxa, valorMes, listaFinanciamento);

                tableFinanciamento.setItems(finSAC);
                mostarResultados();
            } else if (tipoAmortizacao.equals("Price")) {
                IFinanciamento financiamento = new FinanciamentoPrice();
                ObservableList<Financiamento> finPrice = financiamento.calcular(valorPrincipal, taxa, valorMes, listaFinanciamento);

                tableFinanciamento.setItems(finPrice);
                mostarResultados();
            }
        }
    }

    //Função responsável por verificar se os valores dos inputs são igual a zero
    private boolean isZero(double valorPrincipal, double taxa, int valorMes){
        if (valorPrincipal <= 0 || valorMes <= 0 || taxa <= 0)
            return true;
        else
            return false;
    }

    //Função responsável por exibir os dados do resumo financeiro
    private void mostarResultados(){
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

    //Função responsável por limpar os dados dos TextFields e resumo financeiro
    public void limpar(){
        lblCustoTotal.setVisible(false);
        lblJuros.setVisible(false);
        lblValorFinanciado.setVisible(false);

        listaFinanciamento.clear();
        txfValorPrincipal.setText("");
        txfTaxa.setText("");
        txfMeses.setText("");

        AmortizacaoChoiceBox.setValue(null);

        txfValorPrincipal.requestFocus();
    }

}

