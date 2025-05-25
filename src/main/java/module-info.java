module com.example.practicaultima {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.example.practicaultima to javafx.fxml;
    exports com.example.practicaultima;
}