public class CentralEolica extends UnidadEnergia{
    public CentralEolica() {
        super("Central Eólica", 100, 25, 20, 4, 2,1);
    }

    @Override
    public void habilidad(Unidad objetivo, Tablero tablero) {
        if (this.getMaximoUsoHabilidad() <= 0) {
            return;
        }
        objetivo.setRangoMovimiento(objetivo.getRangoMovimiento() - 1); //Por el viento, el enemigo atacado pierde rango de movimiento
        this.atacar(objetivo, tablero);
        this.setMaximoUsoHabilidad(this.getMaximoUsoHabilidad() - 1);
    }
}
