package com.example.practicaultima;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CargarLogsController {
    @FXML
    public Button VerLogs;
    @FXML
    public Button VolverMenuInicial;
    @FXML
    protected void onLogsButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo de log");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de log", "*.log"));
        fileChooser.setInitialDirectory(new File("logs")); // Cambia a tu carpeta real de logs

        Stage stage = (Stage) VerLogs.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            StringBuilder contenido = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivoSeleccionado))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
            } catch (IOException e) {
                contenido.append("Error al leer el archivo.");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Log: " + archivoSeleccionado.getName());
            alert.setHeaderText(null);
            alert.setContentText(contenido.toString());
            alert.showAndWait();
        }
    }
    @FXML
    protected void onVolverMenuInicialClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) VolverMenuInicial.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
