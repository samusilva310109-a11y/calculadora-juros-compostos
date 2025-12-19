package br.com.samuel.calculadorajuroscompostos.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculadoraApplication extends Application {

   @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/br/com/samuel/calculadorajuroscompostos/resources/view/telaCalculadora.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/br/com/samuel/calculadorajuroscompostos/resources/style/telainicial.css").toExternalForm());

        scene.getRoot();
        stage.setScene(scene);
        stage.setTitle("Calculadora Juros Compostos");
        stage.setResizable(false);
        stage.show();


    }
}
