package ProgramaPrincipal;

public class CentralHidroelectrica extends UnidadEnergia{
    public CentralHidroelectrica() {
        super("Hidroeléctrica", 120, 30, 25, 3, 2,1);
    }
    @Override
    public void habilidad(Unidad objetivo, Tablero tablero) {
        if (this.getMaximoUsoHabilidad() <= 0){
            return;
        }
        for (int i=0; i<tablero.obtenerTodasLasUnidades().getNumElementos();i++){
            Unidad obj = tablero.obtenerTodasLasUnidades().getElemento(i);
            if (!(obj.esEnergia())) {
                this.atacar(obj, tablero); //Todos reciben daño a causa de la "inundacion"
            }
        }
        this.setMaximoUsoHabilidad(this.getMaximoUsoHabilidad()-1);
    }
}