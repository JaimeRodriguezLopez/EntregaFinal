package com.example.practicaultima;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import ProgramaPrincipal.*;
import java.io.FileWriter;
import java.io.IOException;

public class EditorTableroController {

    @FXML
    private GridPane gridPane;
    @FXML
    public Button GuardarButton;
    private Tablero tablero;
    private String nombre;


    public void inicializar(int filas, int columnas, String nombre) {
        this.tablero = new Tablero(filas, columnas);
        pintarTablero();
        this.nombre = nombre;
    }

    private void pintarTablero() {
        gridPane.getChildren().clear();//Limpia el gridpane de toda cosa anteriror
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        for (int i = 0; i < tablero.getFilas(); i++) {//lo rellena
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Casilla casilla = tablero.getCasilla(i, j);
                Button btn = new Button();

                actualizarBotonCasilla(btn, casilla);

                int x = i;
                int y = j;
                btn.setOnAction(e -> {
                    alternarCasilla(x, y, btn);
                });

                btn.setPrefSize(40, 40);
                gridPane.add(btn, j, i);
            }
        }
    }

    private void alternarCasilla(int x, int y, Button btn) {//Cambiar de normal a montaña y viceversa
        Casilla casilla = tablero.getCasilla(x, y);
        if (casilla.getCostoMovimiento() == 1) {
            casilla = new Casilla(2, 20); // Montaña
        } else {
            casilla = new Casilla(1, 0); // Normal
        }

        tablero.getCasillas()[x][y] = casilla;
        actualizarBotonCasilla(btn, casilla);
    }

    private void actualizarBotonCasilla(Button btn, Casilla casilla) {//Depende de lo qeu sea cambiar el color
        if (casilla.getCostoMovimiento() == 2) {
            btn.setText("M");
            btn.setStyle("-fx-background-color: #8B4513; -fx-text-fill: white;"); // Marrón montaña
        } else {
            btn.setText("N");
            btn.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;"); // Verde normal
        }
    }

    @FXML
    private void onGuardarButtonClick() {//guardar en json en una carpeta dada
        try {
            int filas = tablero.getFilas();
            int columnas = tablero.getColumnas();
            CasillaData[][] casillasData = new CasillaData[filas][columnas];
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    casillasData[i][j] = new CasillaData(tablero.getCasilla(i, j));
                }
            }

            String nombreArchivo = "tableros/" + nombre + ".json";
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(nombreArchivo)) {
                gson.toJson(casillasData, writer);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setHeaderText(null);
            alert.setContentText("Tablero guardado correctamente en " + nombreArchivo);
            alert.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al guardar el tablero.");
            alert.showAndWait();
        }
    }


}


