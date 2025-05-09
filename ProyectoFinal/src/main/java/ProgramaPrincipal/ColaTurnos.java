package ProgramaPrincipal;

import ProgramaPrincipal.Estructuras.Iterador;
import ProgramaPrincipal.Estructuras.ListaSimplementeEnlazada;

public class ColaTurnos {
    private ListaSimplementeEnlazada<Jugador> jugadores;
    private Iterador<Jugador> turno;

    public ColaTurnos() {
        jugadores = new ListaSimplementeEnlazada<>();
        turno= jugadores.getIterador();
    }
    public void agregarJugador(Jugador j) {
        jugadores.addCiclo(j);

    }
    public Jugador siguienteTurno() {
        return turno.next();

    }
}

