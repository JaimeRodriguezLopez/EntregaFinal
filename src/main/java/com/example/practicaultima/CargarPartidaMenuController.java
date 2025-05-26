package com.example.practicaultima;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import ProgramaPrincipal.CasillaData;
import ProgramaPrincipal.GestorJSON;
import ProgramaPrincipal.Partida;
import ProgramaPrincipal.Tablero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CargarPartidaMenuController {
    @FXML
    public Button VolverDeCargarPartida;
    @FXML
    public Button Seleccionar;
    @FXML
    protected void VolverButtonClick(){//Vuelve menu principal
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) VolverDeCargarPartida.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onSeleccionarPartidaClick() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecciona una partida guardada");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Partida JSON", "*.json"));
            fileChooser.setInitialDirectory(new File("Partidas"));

            Stage stage = (Stage) Seleccionar.getScene().getWindow();
            File archivoSeleccionado = fileChooser.showOpenDialog(stage);

            if (archivoSeleccionado == null) return;

            String ruta = archivoSeleccionado.getAbsolutePath();
            String nombreSinExtension = ruta.substring(0, ruta.lastIndexOf(".json"));

            Partida partida = GestorJSON.cargarPartida(nombreSinExtension);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaPartida.fxml"));
            Parent root = loader.load();
            VistaPartidaController controller = loader.getController();
            controller.inicializar(partida);

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar la partida o la vista de partida.");
        } catch (MovimientoFueraDelTableroException e) {
            throw new RuntimeException(e);
        } catch (CasillaOcupadaException e) {
            throw new RuntimeException(e);
        }
    }
    private void mostrarError(String mensaje) {//Enseñar errores
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
