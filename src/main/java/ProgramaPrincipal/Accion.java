package ProgramaPrincipal;

public class Accion {
    private String nombre;

    public Accion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean esMover() {
        return nombre.equals("MOVER");
    }

    public boolean esAtacar() {
        return nombre.equals("ATACAR");
    }

    @Override
    public String toString() {
        return nombre;
    }
}

