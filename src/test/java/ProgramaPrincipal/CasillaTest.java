package ProgramaPrincipal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CasillaTest {
    Casilla casilla = new Casilla(2, 10);
    Unidad unidad = new CentralNuclear();
    @Test
    void testConstr() {
        //De nuevo, testeamos que el constructor de arriba funcione:
        assertEquals(2, casilla.getCostoMovimiento());
        assertEquals(10, casilla.getModificadorDefensa());
        assertFalse(casilla.isOcupada());
        assertNull(casilla.getUnidad());
    }

    @Test
    void setUnidad() {
        casilla.setUnidad(unidad);
        assertTrue(casilla.isOcupada());
        assertEquals(unidad, casilla.getUnidad());
        //Si fuese nulo..:
        casilla.setUnidad(unidad);
        assertTrue(casilla.isOcupada());

        casilla.setUnidad(null);
        assertFalse(casilla.isOcupada());
        assertNull(casilla.getUnidad());

    }
}
