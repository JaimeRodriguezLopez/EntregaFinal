package com.finaleddmdp.proyectofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimerMenuCreacionDeTablerosController {
    public Button cancelarButton;
    public Button continuarButton;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SegundoMenuCreacionTableros.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) continuarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
