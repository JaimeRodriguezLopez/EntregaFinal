package ProgramaPrincipal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PosicionTest {
    private Posicion posicion = new Posicion(3, 5);

    @Test
    void testConstructor() {
        assertEquals(3, posicion.getX());
        assertEquals(5, posicion.getY());
        //Con esto tmb probamos los getters de posicion
    }

    @Test
    void esIgual() {
        //Si tienen la misma pos:
        Posicion Pos1 = new Posicion(3, 5);
        assertTrue(posicion.esIgual(Pos1));
        //Si es dif:
        Posicion Pos2 = new Posicion(2, 4);
        assertFalse(posicion.esIgual(Pos2));
        //Si fuesen directamentela misma instancia:
        assertTrue(posicion.esIgual(posicion));
        //Si es nula:
        assertFalse(posicion.esIgual(null));
        //Si nos volvemos locos:
        assertFalse(posicion.esIgual("no tengo nadita que ver"));
    }
}