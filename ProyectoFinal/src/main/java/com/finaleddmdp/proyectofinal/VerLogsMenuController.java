package com.finaleddmdp.proyectofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class VerLogsMenuController {
    public Button volverMenuPrincipalButton;

    @FXML
    protected void onVolverMenuPrincipalButtonClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipalMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) volverMenuPrincipalButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
