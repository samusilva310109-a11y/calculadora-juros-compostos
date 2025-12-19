package br.com.samuel.calculadorajuroscompostos.controller;


import br.com.samuel.calculadorajuroscompostos.model.JurosCompostos;
import br.com.samuel.calculadorajuroscompostos.repository.CalculadoraRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TelaCalculadoraController {

    @FXML
    private Label lblMontante;

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

            double CapitalInicial = Double.parseDouble(TextCapitalInicial);
            double Juros = Double.parseDouble(TextJuros);
            int Tempo = Integer.parseInt(TextTempo);

            jc.setCapitalInicial(CapitalInicial);
            jc.setTaxa(Juros);
            jc.setTempo(Tempo);

            double montante = cr.calcularMontante(jc.getCapitalInicial(), jc.getTaxa(), jc.getTempo());
            mostrarMontante(montante);
        }
    }

    public void mostrarMontante(double montante){
        String montanteFormatado = String.format("R$%,.2f", montante);

        lblMontante.setVisible(true);
        lblMontante.setText(montanteFormatado);
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
            txfCapitalInicial.requestFocus();
        }

    }

}
