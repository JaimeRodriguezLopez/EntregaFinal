package ProgramaPrincipal;

public class Casilla {
    private int costoMovimiento;
    private int modificadorDefensa;
    private boolean ocupada;
    private Unidad unidad;

    public Casilla(int costoMovimiento, int modificadorDefensa) {
        this.costoMovimiento = costoMovimiento;
        this.modificadorDefensa = modificadorDefensa;
        this.ocupada = false;
        this.unidad = null;
    }
    public int getCostoMovimiento() {
        return costoMovimiento;
    }
    public int getModificadorDefensa() {
        return modificadorDefensa;
    }
    public boolean isOcupada() {
        return ocupada;
    }
    public Unidad getUnidad() {
        return unidad;
    }
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
        this.ocupada = (unidad != null);
    }
}
