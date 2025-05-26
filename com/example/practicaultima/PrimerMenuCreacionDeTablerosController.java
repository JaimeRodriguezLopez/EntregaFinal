package com.example.practicaultima;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimerMenuCreacionDeTablerosController {
    @FXML
    public Button cancelarButton;
    @FXML
    public Button continuarButton;
    @FXML
    private TextField nombreTableroTextField;
    @FXML
    private TextField anchoTableroTextField;
    @FXML
    private TextField alturaTableroTextField;

    @FXML
    protected void onCancelarButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));//Vuelve al menu Principal
            Parent root = loader.load();
            Stage stage = (Stage) cancelarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarError("No se pudo volver al menú principal.");
        }
    }

    @FXML
    protected void onContinuarButtonClick() {
        String nombreTablero = nombreTableroTextField.getText().trim();//Pilla los valores que tienes escritos
        String anchoTablero = anchoTableroTextField.getText().trim();
        String alturaTablero = alturaTableroTextField.getText().trim();

        if (nombreTablero.isEmpty()) {//Manejos de errores
            mostrarError("Introduce un nombre para el tablero");
            return;
        }
        int alto, ancho;
        try {
            alto = Integer.parseInt(alturaTablero);
            ancho = Integer.parseInt(anchoTablero);
            if (ancho < 5 || alto < 5) {
                mostrarError("Las dimensiones deben ser mayores o iguales a 5");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("Introduce valores numéricos válidos para las dimensiones");
            return;
        }

        try {//Abre el editor de tablero con los atributos dados
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editorTablero.fxml"));
            Parent root = loader.load();


            EditorTableroController controller = loader.getController();
            controller.inicializar(ancho, alto, nombreTablero);


            Stage stage = new Stage();
            stage.setTitle("Editor de ProgramaPrincipal.Tablero - " + nombreTablero);
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) continuarButton.getScene().getWindow()).close();
        } catch (IOException e) {
            mostrarError("Error al abrir el editor de tablero");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
