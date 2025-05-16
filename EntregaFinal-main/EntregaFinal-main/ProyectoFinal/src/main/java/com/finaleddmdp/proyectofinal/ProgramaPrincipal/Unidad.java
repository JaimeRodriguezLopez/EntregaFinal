package com.finaleddmdp.proyectofinal.ProgramaPrincipal;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

public abstract class Unidad {
    @Expose
    protected int coordX;
    @Expose
    protected int coordY;
    @Expose
    protected String nombre;
    @Expose
    protected int hp;
    @Expose
    protected int maxHp;
    @Expose
    protected int ataque;
    @Expose
    protected int defensa;
    @Expose
    protected int RgMovimiento;
    @Expose
    protected int RgAtaque;
    @Expose
    protected boolean vivo;
    @Expose
    protected int movimientosRestantes;
    @Expose
    protected boolean jugable;
    @Expose
    protected Tablero tablero;


    public Unidad(String nombre, int hp, int ataque, int def, int RgMovimiento, int RgAtaque, boolean jugable) {
        this.nombre = nombre;
        this.hp = hp;
        this.maxHp = hp;
        this.ataque = ataque;
        this.defensa = def;
        this.RgMovimiento = RgMovimiento;
        this.vivo = true;
        this.RgAtaque = RgAtaque;
        this.movimientosRestantes = RgMovimiento;
        this.jugable = jugable;
    }
    public void setCoordX(int coordX){
        this.coordX=coordX;
    }
    public void setCoordY(int coordY){
        this.coordY=coordY;
    }
    public int getCoordX(){
        return coordX;
    }
    public int getCoordY(){
        return coordY;
    }
    public String getNombre(){
        return nombre;
    }
    public int getRgAtaque(){
        return RgAtaque;
    }
    public boolean getVivo(){
        return vivo;
    }
    public void setVivo(boolean vivo){
        this.vivo = vivo;
    }
    public void setHp(int hp){
        this.hp = hp;
    }
    public int getHp(){
        return hp;
    }
    public int getMaxHp(){
        return maxHp;
    }
    public void setMaxHp(int maxHp){
        this.maxHp = maxHp;
    }
    public int getAtaque(){
        return ataque;
    }
    public void setAtaque(int ataque){
        this.ataque = ataque;
    }
    public int getDefensa(){
        return defensa;
    }
    public void setDefensa(int defensa){
        this.defensa = defensa;
    }
    public int getRgMovimiento(){
        return RgMovimiento;
    }
    public void setRgMovimiento(int RgMovimiento){
        this.RgMovimiento = RgMovimiento;
    }
    public int getMovimientosRestantes(){
        return movimientosRestantes;
    }
    public void setMovimientosRestantes(int movimientosRestantes){
        this.movimientosRestantes = movimientosRestantes;
    }
    public void setJugable(boolean jugable){
        this.jugable = jugable;
    }
    public boolean isJugable(){
        return jugable;
    }
    public void setTablero(Tablero tablero){
        this.tablero = tablero;
    }
    public Tablero getTablero(){
        return tablero;
    }


    public void atacar(@NotNull Unidad objetivo) {
        if (!objetivo.getVivo()) return;

        int variacion = (int)(Math.random() * 3);
        int dano = variacion*this.ataque - objetivo.defensa;

        if (dano < 0) dano = 0;

        objetivo.hp -= dano;


        if (objetivo.hp <= 0) {
            objetivo.hp = 0;
            objetivo.vivo = false;
        }
    }


}
