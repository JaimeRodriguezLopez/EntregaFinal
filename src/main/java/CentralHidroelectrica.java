public class CentralHidroelectrica extends UnidadEnergia{
    public CentralHidroelectrica() {
        super("Central Hidroeléctrica", 120, 30, 25, 3, 2,1);
    }
    @Override
    public void habilidad(Unidad objetivo, Tablero tablero) {
        if (this.getMaximoUsoHabilidad() <= 0){
            return;
        }
        for (int i=0; tablero.obtenerTodasLasUnidades().getNumElementos()<i;i++){//(Unidad obj : tablero.obtenerTodasLasUnidades() ) {
            Unidad obj = tablero.obtenerTodasLasUnidades().getElemento(i);
            if (!(obj.esEnergia())) {
                this.atacar(objetivo, tablero); //Todos reciben daño a causa de la "contaminacion"
            }
        }
        this.setMaximoUsoHabilidad(this.getMaximoUsoHabilidad()-1);
    }
}
