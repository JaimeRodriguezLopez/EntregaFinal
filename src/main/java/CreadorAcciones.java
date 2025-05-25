public class CreadorAcciones {
    public Accion crearMover() {
        return new Accion("MOVER");
    }

    public Accion crearAtacar() {
        return new Accion("ATACAR");
    }
}
