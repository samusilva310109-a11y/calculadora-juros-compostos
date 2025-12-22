package br.com.samuel.calculadorajuroscompostos.controller;


import br.com.samuel.calculadorajuroscompostos.model.JurosCompostos;
import br.com.samuel.calculadorajuroscompostos.repository.CalculadoraRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TelaCalculadoraController {

    @FXML
    private Label lblMontante;

    @FXML
    private Label lblJuros;

    @FXML
    private Label lblCapitalInicial;

    @FXML
    private Button btCalcular;

    @FXML
    private Button btLimpar;

    @FXML
    private TextField txfCapitalInicial;

    @FXML
    private TextField txfJuros;

    @FXML
    private TextField txfTempo;


    public void initialize(){
        lblMontante.setVisible(false);
        lblCapitalInicial.setVisible(false);
        lblJuros.setVisible(false);
    }

    public void calcular(){

        if (txfCapitalInicial.getText().isEmpty() || txfJuros.getText().isEmpty() || txfTempo.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Preencha todos os campos!!");
            alert.show();
        }else {
            String TextCapitalInicial = txfCapitalInicial.getText();
            String TextJuros = txfJuros.getText();
            String TextTempo = txfTempo.getText();

            JurosCompostos jc = new JurosCompostos();
            CalculadoraRepository cr = new CalculadoraRepository();

            double capitalInicial = Double.parseDouble(TextCapitalInicial);
            double taxa = Double.parseDouble(TextJuros);
            int tempo = Integer.parseInt(TextTempo);

            jc.setCapitalInicial(capitalInicial);
            jc.setTaxa(taxa);
            jc.setTempo(tempo);

            double montante = cr.calcularMontante(jc.getCapitalInicial(), jc.getTaxa(), jc.getTempo());
            double juros = cr.calcularJuros(jc.getCapitalInicial(), montante);

            mostrarResultado(montante, juros,  capitalInicial);
        }
    }

    public void mostrarResultado(double montante, double juros, double capitalInicial) {
        String montanteFormatado = String.format("R$%,.2f", montante);
        String jurosFormatado = String.format("R$%,.2f", juros);
        String capitalFormatado = String.format("R$%,.2f", capitalInicial);

        lblMontante.setVisible(true);
        lblMontante.setText(montanteFormatado);

        lblJuros.setVisible(true);
        lblJuros.setText(jurosFormatado);

        lblCapitalInicial.setVisible(true);
        lblCapitalInicial.setText(capitalFormatado);
    }

    public void limpar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Deseja limpar?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        if(alert.showAndWait().get() == ButtonType.YES){
            txfCapitalInicial.clear();
            txfJuros.clear();
            txfTempo.clear();

            lblMontante.setVisible(false);
            lblCapitalInicial.setVisible(false);
            lblJuros.setVisible(false);

            txfCapitalInicial.requestFocus();
        }

    }

}
