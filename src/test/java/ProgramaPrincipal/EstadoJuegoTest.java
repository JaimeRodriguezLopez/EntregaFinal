package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadoJuegoTest {
    Partida partida = new Partida(3, 3, 5);
    EstadoJuego estado = new EstadoJuego(partida);

    @Test
    void testConstructor() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        assertEquals(3, estado.getFilas());
        assertEquals(3, estado.getColumnas());
        assertTrue(estado.isTurnoJugador());
        assertEquals(0, estado.getTurnos());
        assertNotNull(estado.getCasillasData());
        assertNotNull(estado.getUnidadesJugador());
        assertNotNull(estado.getUnidadesIA());
    }

    @Test
    void testSettersYGetters() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        estado.setFilas(5);
        estado.setColumnas(6);
        estado.setTurnoJugador(false);
        estado.setTurnos(10);
        estado.setFrecuenciaNuevaUnidad(3);
        assertEquals(5, estado.getFilas());
        assertEquals(6, estado.getColumnas());
        assertFalse(estado.isTurnoJugador());
        assertEquals(10, estado.getTurnos());
        assertEquals(3, estado.getFrecuenciaNuevaUnidad());
    }

}