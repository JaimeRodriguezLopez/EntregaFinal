package com.finaleddmdp.proyectofinal.ProgramaPrincipal;

import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.Iterador;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.ListaSimplementeEnlazada;

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
    public void reiniciarMovimientos() {
        Iterador<Unidad> iterador = unidades.getIterador();
        while (iterador.hasNext()) {
            Unidad unidad = iterador.next();
            if (unidad.getVivo()) {
                unidad.setMovimientosRestantes(unidad.getRgMovimiento());
            }
        }
    }
    public void eliminarUnidadesMuertas() {
        Iterador<Unidad> iterador = unidades.getIterador();
        while (iterador.hasNext()) {
            Unidad unidad = iterador.next();
            if (!unidad.getVivo()) {
                iterador.delete();
            }
        }
    }
    public abstract void terminarTurno(Tablero tablero);
}

