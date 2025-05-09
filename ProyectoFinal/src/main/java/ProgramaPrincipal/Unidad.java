package ProgramaPrincipal;

import org.jetbrains.annotations.NotNull;

public abstract class Unidad {
    protected int coordX;
    protected int coordY;
    protected String nombre;
    protected int hp;
    protected int maxHp;
    protected int ataque;
    protected int defensa;
    protected int RgMovimiento;
    protected int RgAtaque;
    protected boolean vivo;
    protected int movimientosRestantes;
    protected boolean jugable;


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


    public void atacar(@NotNull Unidad objetivo) {
        if (!objetivo.getVivo()) return;

        int variacion = (int)(Math.random() * 3);
        int dano = variacion*this.ataque - objetivo.defensa;

        if (dano < 0) dano = 0;

        objetivo.hp -= dano;

        System.out.println(this.nombre + " atacó a " + objetivo.getNombre() + " causando " + dano + " de daño.");

        if (objetivo.hp <= 0) {
            objetivo.hp = 0;
            objetivo.vivo = false;
            System.out.println(objetivo.getNombre() + " ha muerto.");
        }
    }


}
