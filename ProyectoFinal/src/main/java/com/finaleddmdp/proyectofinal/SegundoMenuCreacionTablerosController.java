package com.finaleddmdp.proyectofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SegundoMenuCreacionTablerosController {

    public Button botonPrueba;
    public Button terminarBoton;
    @FXML
    protected void abrirVentanaEmergente() {
        Stage ventanaEmergente = new Stage();
        CheckBox fuerza = new CheckBox("Fuerza");
        CheckBox inteligencia = new CheckBox("Inteligencia");
        CheckBox velocidad = new CheckBox("Velocidad");
        Button aceptar = new Button("Aceptar");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                new Label("Selecciona los atributos:"),
                fuerza,
                inteligencia,
                velocidad,
                aceptar
        );
        aceptar.setOnAction(e -> {
            ventanaEmergente.close();
        });
        ;

        Scene escena = new Scene(layout, 250, 200);
        ventanaEmergente.setTitle("Seleccionar Atributos");
        ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
        ventanaEmergente.setScene(escena);
        ventanaEmergente.showAndWait();
    }
    @FXML
    protected void onTerminarButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigPaetida.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) terminarBoton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


