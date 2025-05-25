public class CasillaData {
    private int costoMovimiento;
    private int modificadorDefensa;
    private boolean ocupada;
    private UnidadData unidadData;

    public CasillaData() {}

    public CasillaData(Casilla casilla) {
        this.costoMovimiento = casilla.getCostoMovimiento();
        this.modificadorDefensa = casilla.getModificadorDefensa();
        this.ocupada = casilla.isOcupada();
        if (casilla.getUnidad() != null) {
            this.unidadData = new UnidadData(casilla.getUnidad());
        }
    }

    // Getters y setters
    public int getCostoMovimiento() { return costoMovimiento; }
    public void setCostoMovimiento(int costoMovimiento) { this.costoMovimiento = costoMovimiento; }

    public int getModificadorDefensa() { return modificadorDefensa; }
    public void setModificadorDefensa(int modificadorDefensa) { this.modificadorDefensa = modificadorDefensa; }

    public boolean isOcupada() { return ocupada; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }

    public UnidadData getUnidadData() { return unidadData; }
    public void setUnidadData(UnidadData unidadData) { this.unidadData = unidadData; }
}
