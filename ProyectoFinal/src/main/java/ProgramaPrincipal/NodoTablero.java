package ProgramaPrincipal;

public class NodoTablero {
    int coordX;
    int coordY;
    NodoTablero norte;
    NodoTablero sur;
    NodoTablero este;
    NodoTablero oeste;
    boolean penetrable;
    Unidad unidadEnCasilla;
    int[] modificadores;
    boolean ocupado;
    int usoMovimiento;
    //Objeto ObjetoEnCasilla;


    public NodoTablero(int X, int Y) {
        coordX = X;
        coordY = Y;
        this.norte = null;
        this.sur = null;
        this.este = null;
        this.oeste = null;
        this.penetrable = true;//Esta dice si puede haber una unidad en ella
        this.modificadores = new int[6];// [Δhp, Δataque, Δdefensa, ΔRgMov, ΔRgAtq]
        this.unidadEnCasilla = null;//En esta variable se pone la unidad en casilla
        this.ocupado = false;
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
        return ocupado;
     }
    public void setModificadores(int hp, int atq, int def, int RgMov, int RgAtq,int usoMovimiento) {
        int[] mod = new int[]{hp, atq, def, RgMov, RgAtq, usoMovimiento};
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

    public int getModUsoMovimiento() {
        return modificadores[5];
    }

    public void setUnidadEnCasilla(Unidad unidadEnCasilla) {
        this.unidadEnCasilla = unidadEnCasilla;
        this.ocupado = true;
        unidadEnCasilla.setCoordX(getCoordX());
        unidadEnCasilla.setCoordY(getCoordY());
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

}