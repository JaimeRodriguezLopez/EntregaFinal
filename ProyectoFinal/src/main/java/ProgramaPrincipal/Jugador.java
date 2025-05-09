package ProgramaPrincipal;

import ProgramaPrincipal.Estructuras.ListaSimplementeEnlazada;

import ProgramaPrincipal.Unidad;
public abstract class Jugador {
    protected ListaSimplementeEnlazada<Unidad> unidades;
    protected String faccion; //Electricas--Virus
    public Jugador(String faccion) {
        this.faccion = faccion;
        this.unidades = new ListaSimplementeEnlazada<>();
    }
    public String getFaccion() {
        return faccion;
    }
    public ListaSimplementeEnlazada<Unidad> getUnidades() {
        return unidades;
    }
    public abstract void terminarTurno(Tablero tablero);
}

