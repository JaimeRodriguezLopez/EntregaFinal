module com.finaleddmdp.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jdk.compiler;
    requires annotations;
    requires com.google.gson;

    opens com.finaleddmdp.proyectofinal to com.google.gson, javafx.fxml;
    opens com.finaleddmdp.proyectofinal.ProgramaPrincipal to com.google.gson;

    exports com.finaleddmdp.proyectofinal;
}