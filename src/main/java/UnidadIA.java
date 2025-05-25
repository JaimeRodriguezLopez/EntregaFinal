public abstract class UnidadIA extends Unidad {
    public UnidadIA(String nombre, int hp, int ataque, int defensa, int rangoMovimiento, int rangoAtaque, int MaxUsoHab) {
        super(nombre, hp, ataque, defensa, rangoMovimiento, rangoAtaque, MaxUsoHab);
    }

    @Override
    public boolean esEnergia() {
        return false;
    }
}
