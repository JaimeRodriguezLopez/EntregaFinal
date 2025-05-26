package ProgramaPrincipal;

public abstract class UnidadEnergia extends Unidad {
    public UnidadEnergia(String nombre, int hp, int ataque, int defensa, int rangoMovimiento, int rangoAtaque, int MaxUsoHabilidd) {
        super(nombre, hp, ataque, defensa, rangoMovimiento, rangoAtaque, MaxUsoHabilidd);
    }

    @Override
    public boolean esEnergia() {
        return true;
    }
    public abstract void habilidad(Unidad objetivo, Tablero tablero);
    //Se va a especificar en cada tipo de ficha

}

