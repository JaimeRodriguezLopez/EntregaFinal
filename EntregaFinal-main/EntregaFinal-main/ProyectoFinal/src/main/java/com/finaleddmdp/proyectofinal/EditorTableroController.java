package com.finaleddmdp.proyectofinal;

import com.finaleddmdp.proyectofinal.ProgramaPrincipal.NodoTablero;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Tablero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditorTableroController {
    @FXML
    private GridPane gridTablero;

    private int ancho;
    private int altura;
    private String nombre;
    private NodoTableroModel[][] nodos;

    public void inicializar(int ancho, int altura, String nombre) {
        this.ancho = ancho;
        this.altura = altura;
        this.nombre = nombre;
        nodos = new NodoTableroModel[ancho][altura];

        gridTablero.getChildren().clear();
        gridTablero.getColumnConstraints().clear();
        gridTablero.getRowConstraints().clear();

        // Configurar columnas y filas dinámicas
        for (int i = 0; i < ancho; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            gridTablero.getColumnConstraints().add(cc);
        }

        for (int i = 0; i < altura; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            gridTablero.getRowConstraints().add(rc);
        }

        // Crear botones
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < altura; y++) {
                nodos[x][y] = new NodoTableroModel(x, y);
                Button btn = new Button(x + "," + y);
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setStyle("-fx-font-size: 10pt;");

                GridPane.setFillWidth(btn, true);
                GridPane.setFillHeight(btn, true);

                int finalX = x;
                int finalY = y;
                btn.setOnAction(e -> abrirEditorCelda(finalX, finalY));
                btn.setStyle(nodos[x][y].isPenetrable() ? "-fx-background-color: #90EE90" : "-fx-background-color: #FFB6C1");
                gridTablero.add(btn, x, y);
            }
        }
    }
    @FXML
    private void onTerminarButtonClick() {
        Tablero tableroFinal = new Tablero(ancho, altura);
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < altura; y++) {
                NodoTableroModel nodoOriginal = nodos[x][y];
                NodoTablero nodoDestino = tableroFinal.getNodoTablero(x, y);
                nodoDestino.setPenetrable(nodoOriginal.isPenetrable());
                nodoDestino.setCostoMovimiento(nodoOriginal.getCostoMovimiento());
                nodoDestino.setModificadores(nodoOriginal.getModificadorAtaque(),nodoOriginal.getModificadorDefensa(),nodoOriginal.getModificadorRangoMov(),nodoOriginal.getModificadorRangoAtq());
            }
        }
        StringBuilder ruta =new StringBuilder(this.nombre);
        ruta.append(".json");
        Tablero.guardarTablero(tableroFinal, ruta.toString());
        ((Stage) gridTablero.getScene().getWindow()).close();
    }



    /*private void iniciarJuego() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaJuego.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            VistaJuegoController controller = loader.getController();
            controller.inicializar(tableroFinal);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



    private void abrirEditorCelda(int x, int y) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editorCelda.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));

            EditorCeldaController controller = loader.getController();
            controller.setModelo(nodos[x][y]);

            stage.showAndWait();

            // Actualizar apariencia del botón después de editar
            int index = x * ancho + y;
            Button btn = (Button) gridTablero.getChildren().get(index);
            btn.setStyle(nodos[x][y].isPenetrable() ? "-fx-background-color: #90EE90" : "-fx-background-color: #FFB6C1");
        } catch (Exception e) {            e.printStackTrace();
        }
    }
}
