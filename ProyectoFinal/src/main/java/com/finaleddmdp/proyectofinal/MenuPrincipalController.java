package com.finaleddmdp.proyectofinal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;


public class MenuPrincipalController {
    public Button exitGameButton;
    public Button nuevaPartidaButton;
    public Button cargarPartidaButton;
    public Button verTablerosButton;
    public Button verLogsButton;
    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }
    @FXML
    protected void onStartGameButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevaPartidaMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nuevaPartidaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onCargarPartidaButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CargarPartidaMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nuevaPartidaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onVerTablerosButtonClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TablerosMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) verTablerosButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onVerLogsButtonClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VerLogsMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) verLogsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}