package com.finaleddmdp.proyectofinal.ProgramaPrincipal;

import com.finaleddmdp.proyectofinal.NodoTableroModel;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.ListaBasica;
import com.google.gson.annotations.Expose;

public class NodoTablero {
    int coordX;
    int coordY;
    private transient NodoTablero norte;
    private transient NodoTablero sur;
    private transient NodoTablero este;
    private transient NodoTablero oeste;

    boolean penetrable;
    Unidad unidadEnCasilla;
    int[] modificadores;
    boolean ocupado;
    int costoMovimiento;
    private transient Tablero tablero;
    //Objeto ObjetoEnCasilla;

    public NodoTablero(int X, int Y) {
        coordX = X;
        coordY = Y;
        this.norte = null;
        this.sur = null;
        this.este = null;
        this.oeste = null;
        this.penetrable = true;//Esta dice si puede haber una unidad en ella
        this.modificadores = new int[5];// [Δhp, Δataque, Δdefensa, ΔRgMov, ΔRgAtq]
        this.unidadEnCasilla = null;//En esta variable se pone la unidad en casilla
        this.ocupado = false;
        this.costoMovimiento = 1;
        this.tablero = null;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public NodoTablero getNorte() {
        return norte;
    }

    public void setNorte(NodoTablero norte) {
        this.norte = norte;
    }

    public NodoTablero getSur() {
        return sur;
    }

    public void setSur(NodoTablero sur) {
        this.sur = sur;
    }

    public NodoTablero getEste() {
        return este;
    }

    public void setEste(NodoTablero este) {
        this.este = este;
    }

    public NodoTablero getOeste() {
        return oeste;
    }

    public void setOeste(NodoTablero oeste) {
        this.oeste = oeste;
    }

    public boolean isPenetrable() {
        return penetrable;
    }

    public void setPenetrable(boolean penetrable) {
        this.penetrable = penetrable;
    }
    public void setOcupado(boolean ocupado){
        this.ocupado=ocupado;
    }
    public boolean getOcupado(){
        return !ocupado;
     }
    public void setModificadores(int atq, int def, int RgMov, int RgAtq) {
        int[] mod = new int[]{ atq, def, RgMov, RgAtq};
        this.modificadores = mod;
    }
    public int getModHP() {
        return modificadores[0];
    }

    public int getModAtaque() {
        return modificadores[1];
    }

    public int getModDefensa() {
        return modificadores[2];
    }

    public int getModRangoMovimiento() {
        return modificadores[3];
    }

    public int getModRangoAtaque() {
        return modificadores[4];
    }

    public int getCostoMovimiento() {
        return this.costoMovimiento;
    }

    public void setCostoMovimiento(int costoMovimiento) {
        this.costoMovimiento = costoMovimiento;
    }
    public Tablero getTablero() {
        return tablero;
    }
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setUnidadEnCasilla(Unidad unidadEnCasilla) {
        this.unidadEnCasilla = unidadEnCasilla;
        this.ocupado = true;
        unidadEnCasilla.setCoordX(getCoordX());
        unidadEnCasilla.setCoordY(getCoordY());
        unidadEnCasilla.setTablero(this.tablero);
    }
    public void eliminarUnidadEnCasilla() {
        if (this.unidadEnCasilla != null) {
            this.unidadEnCasilla.setCoordX(-1);
            this.unidadEnCasilla.setCoordY(-1);
        }
        this.unidadEnCasilla = null;
        this.setOcupado(false);
    }

    public Unidad getUnidadEnCasilla(){
        return this.unidadEnCasilla;
    }
    public ListaBasica<NodoTablero> getVecinosPenetrables(){
        NodoTablero norte = this.norte;
        NodoTablero sur = this.sur;
        NodoTablero este = this.este;
        NodoTablero oeste = this.oeste;
        ListaBasica<NodoTablero> vecinos = new ListaBasica<NodoTablero>(4);
        if ((norte != null)&&(norte.isPenetrable())) {
            if (norte.getUnidadEnCasilla() != null ) {
                if (!norte.getUnidadEnCasilla().isJugable()) {
                    vecinos.add(norte);
                }
            }
        }
        if ((sur != null)&&(sur.isPenetrable())) {//Existe la casilla y puedes entrar
            if (sur.getUnidadEnCasilla() != null ) {//Si entras hay una unidad?
                if (!sur.getUnidadEnCasilla().isJugable()) {//Esa unidad es tuya?
                    vecinos.add(sur);
                }
            }
        }
        if ((este != null)&&(este.isPenetrable())) {
            if (este.getUnidadEnCasilla() != null ) {//Si entras hay una unidad?
                if (!este.getUnidadEnCasilla().isJugable()) {//Esa unidad es tuya?
                    vecinos.add(este);
                }
            }
        }
        if ((oeste != null)&&(oeste.isPenetrable())) {
            if (oeste.getUnidadEnCasilla() != null ) {//Si entras hay una unidad?
                if (!oeste.getUnidadEnCasilla().isJugable()) {//Esa unidad es tuya?
                    vecinos.add(oeste);
                }
            }
        }
        return vecinos;
    }
}