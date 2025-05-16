package com.finaleddmdp.proyectofinal;



public class NodoTableroModel {

    private int x;
    private int y;
    private boolean penetrable;
    private int costoMovimiento;
    private int modificadorAtaque;
    private int modificadorDefensa;
    private int modificadorRangoMov;
    private int modificadorRangoAtq;
    public NodoTableroModel(int y, int x) {
        this.y = y;
        this.x = x;
        this.penetrable = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPenetrable() {
        return penetrable;
    }

    public void setPenetrable(boolean penetrable) {
        this.penetrable = penetrable;
    }

    public int getCostoMovimiento() {
        return costoMovimiento;
    }

    public void setCostoMovimiento(int costoMovimiento) {
        this.costoMovimiento = costoMovimiento;
    }

    public int getModificadorAtaque() {
        return modificadorAtaque;
    }

    public void setModificadorAtaque(int modificadorAtaque) {
        this.modificadorAtaque = modificadorAtaque;
    }

    public int getModificadorDefensa() {
        return modificadorDefensa;
    }

    public void setModificadorDefensa(int modificadorDefensa) {
        this.modificadorDefensa = modificadorDefensa;
    }

    public int getModificadorRangoMov() {
        return modificadorRangoMov;
    }

    public void setModificadorRangoMov(int modificadorRangoMov) {
        this.modificadorRangoMov = modificadorRangoMov;
    }

    public int getModificadorRangoAtq() {
        return modificadorRangoAtq;
    }

    public void setModificadorRangoAtq(int modificadorRangoAtq) {
        this.modificadorRangoAtq = modificadorRangoAtq;
    }
}