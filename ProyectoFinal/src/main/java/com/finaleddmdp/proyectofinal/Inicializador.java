package com.finaleddmdp.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Inicializador extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inicializador.class.getResource("MenuPrincipalMenu.fxml"));
        Scene MainMenu = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Conquista de conocimientos");
        stage.setScene(MainMenu);
        stage.show();
    }


}