package com.finaleddmdp.proyectofinal;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class EditorCeldaController {
    @FXML
    private CheckBox chkPenetrable;
    @FXML
    private Spinner<Integer> spnCostoMov;
    @FXML
    private Spinner<Integer> spnModAtaque;
    @FXML
    private Spinner<Integer> spnModDefensa;
    @FXML
    private Spinner<Integer> spnModRangoMov;
    @FXML
    private Spinner<Integer> spnModRangoAtq;

    private NodoTableroModel modelo;

    public void setModelo(NodoTableroModel modelo) {
        this.modelo = modelo;
        cargarDatos();
    }

    private void cargarDatos() {
        chkPenetrable.setSelected(modelo.isPenetrable());
        spnCostoMov.getValueFactory().setValue(modelo.getCostoMovimiento());
        spnModAtaque.getValueFactory().setValue(modelo.getModificadorAtaque());
        spnModDefensa.getValueFactory().setValue(modelo.getModificadorDefensa());
        spnModRangoMov.getValueFactory().setValue(modelo.getModificadorRangoMov());
        spnModRangoAtq.getValueFactory().setValue(modelo.getModificadorRangoAtq());
    }

    @FXML
    private void guardarCambios() {
        modelo.setPenetrable(chkPenetrable.isSelected());
        modelo.setCostoMovimiento(spnCostoMov.getValue());
        modelo.setModificadorAtaque(spnModAtaque.getValue());
        modelo.setModificadorDefensa(spnModDefensa.getValue());
        modelo.setModificadorRangoMov(spnModRangoMov.getValue());
        modelo.setModificadorRangoAtq(spnModRangoAtq.getValue());

        ((Stage) chkPenetrable.getScene().getWindow()).close();
    }
    @FXML
    public void initialize() {
        spnCostoMov.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        spnModAtaque.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-10, 10, 0));
        spnModDefensa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-10, 10, 0));
        spnModRangoMov.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-5, 5, 0));
        spnModRangoAtq.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-5, 5, 0));
    }

}
