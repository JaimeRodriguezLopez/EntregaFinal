package com.example.practicaultima;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuPrincipalController {
    @FXML
    public Button NuevaPartidaButton;
    @FXML
    public Button CargarPartidaButton;
    @FXML
    public Button SalirButton;
    @FXML
    public Button LogsButton;
    @FXML
    public Button TablerosButton;

    @FXML
    protected void SalirButtonClick(){
        Platform.exit();
    }
    @FXML
    protected void NuevaPartidaButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevaPartidaMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) NuevaPartidaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void CargarPartidaButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CargarPartidaMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) CargarPartidaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void TablerosButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(".fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) TablerosButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void LogsButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(".fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) LogsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

