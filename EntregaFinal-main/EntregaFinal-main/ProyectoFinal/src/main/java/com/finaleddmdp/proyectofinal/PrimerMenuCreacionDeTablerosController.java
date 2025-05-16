package com.finaleddmdp.proyectofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipalMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancelarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onContinuarButtonClick() {
        String nombreTablero = nombreTableroTextField.getText();
        String anchoTablero = anchoTableroTextField.getText();
        String alturaTablero = alturaTableroTextField.getText();

        if (nombreTablero.isEmpty()) {
            mostrarError("Introduce un nombre para el tablero");
            return;
        }
        int alto;
        int ancho;
        try{
            alto = Integer.parseInt(alturaTablero);
            ancho = Integer.parseInt(anchoTablero);
            if (ancho < 5 || alto < 5){
                mostrarError("Las dimensiones deben ser mayores o iguales a 5");
                return;
            }
        } catch (NumberFormatException e){
            mostrarError("Introduce valores numéricos válidos para las dimensiones");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editorTablero.fxml"));
            Parent root = loader.load();

            EditorTableroController controller = loader.getController();
            controller.inicializar(ancho, alto,nombreTablero);

            Stage stage = new Stage();
            stage.setTitle("Editor de Tablero - " + nombreTablero);
            stage.setScene(new Scene(root));
            stage.show();


            ((Stage) continuarButton.getScene().getWindow()).close();
        }catch (IOException e){
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
