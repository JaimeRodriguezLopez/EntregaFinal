package ProgramaPrincipal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccionTest {
    Accion accionMover = new Accion("MOVER");
    Accion accionAtacar = new Accion("ATACAR");;

    @Test
    void getNombre() {
        //Testeamos el constructor de arriba:
        assertEquals("MOVER", accionMover.getNombre());
        assertEquals("ATACAR", accionAtacar.getNombre());
    }

    @Test
    void esMover() {
        assertTrue(accionMover.esMover());
        assertFalse(accionAtacar.esMover());
    }

    @Test
    void esAtacar() {
        assertTrue(accionAtacar.esAtacar());
        assertFalse(accionMover.esAtacar());
    }

    @Test
    void testToString() {
        assertEquals("MOVER", accionMover.toString());
        assertEquals("ATACAR", accionAtacar.toString());
    }
}
