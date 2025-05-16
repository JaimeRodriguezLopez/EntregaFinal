package com.finaleddmdp.proyectofinal.ProgramaPrincipal;

import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.Iterador;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.ListaSimplementeEnlazada;

public class ColaTurnos {
    private ListaSimplementeEnlazada<Jugador> jugadores;
    private Iterador<Jugador> turno;
    private int numTurno;

    public ColaTurnos() {
        jugadores = new ListaSimplementeEnlazada<>();
        turno = jugadores.getIterador();
        numTurno = 0;
    }
    public void agregarJugador(Jugador j) {
        jugadores.addCiclo(j);

    }
    public Jugador siguienteTurno() {
        numTurno++;
        return turno.next();
    }
}

