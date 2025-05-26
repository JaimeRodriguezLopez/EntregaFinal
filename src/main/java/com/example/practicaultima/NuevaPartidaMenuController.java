package com.example.practicaultima;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import ProgramaPrincipal.Casilla;
import ProgramaPrincipal.CasillaData;
import ProgramaPrincipal.Partida;
import ProgramaPrincipal.Tablero;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NuevaPartidaMenuController {
    @FXML
    public Button Volver;
    @FXML
    public Button NuevoTablero;
    @FXML
    public Button NuevaPartidaTablero;

    @FXML
    public TextField FrecuenciaNuevasUnidades;
    @FXML
    protected void VolverButtonClick(){//Vuelve menu principal
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) Volver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void NuevoTableroButtonClick(){//Abre la ventana para crear tabvleros
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimerMenuCreacionTablero.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) NuevoTablero.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onNuevaPartidaTableroClick() {
        try {
            FileChooser fileChooser = new FileChooser();//te abre en la carpeta de tableros y lo eliges
            fileChooser.setTitle("Selecciona un tablero");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tableros JSON", "*.json"));
            fileChooser.setInitialDirectory(new File("tableros"));

            Stage stage = (Stage) NuevaPartidaTablero.getScene().getWindow();
            File archivoSeleccionado = fileChooser.showOpenDialog(stage);

            if (archivoSeleccionado == null) {
                return;
            }
            String frecuenciaTexto = FrecuenciaNuevasUnidades.getText().trim();//Esto es para saber cada cuanto se añade una unidad
            int frecuencia;
            try {
                frecuencia = Integer.parseInt(frecuenciaTexto); //Pasa el numero a int
            } catch (NumberFormatException e) {
                mostrarError("Introduce un número válido para la frecuencia."); //Error no vaya a ser que no hayan puesto numero
                return;
            }

            CasillaData[][] casillasData = cargarTableroDesdeJson(archivoSeleccionado.getAbsolutePath());//Cargar Partida con el tablero seleccionado
            Tablero tablero = construirTableroDesdeData(casillasData);
            Partida partida = new Partida(tablero.getFilas(), tablero.getColumnas(), frecuencia);
            partida.inicializar();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaPartida.fxml"));
            Parent root = loader.load();
            VistaPartidaController controller = loader.getController();
            controller.inicializar(partida);//Abre la partida

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar el tablero o la vista de partida.");
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


    public CasillaData[][] cargarTableroDesdeJson(String rutaArchivo) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(rutaArchivo)) {

            CasillaData[][] casillasData = gson.fromJson(reader, CasillaData[][].class);
            return casillasData;
        }
    }

    public Tablero construirTableroDesdeData(CasillaData[][] casillasData) {//Costruye el tablero
        int filas = casillasData.length;
        int columnas = casillasData[0].length;
        Casilla[][] casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                CasillaData data = casillasData[i][j];
                casillas[i][j] = new Casilla(data.getCostoMovimiento(), data.getModificadorDefensa());
            }
        }
        return new Tablero(filas, columnas, casillas);
    }
}

