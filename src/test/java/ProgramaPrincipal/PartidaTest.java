package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {
    private final int FILAS = 5;
    private final int COLUMNAS = 5;
    private final int FRECUENCIA_NUEVA_UNIDAD = 3;
    Partida partida = new Partida(FILAS, COLUMNAS, FRECUENCIA_NUEVA_UNIDAD);

    @Test
    void testConstructor() {
        assertNotNull(partida.getTablero());
        assertNotNull(partida.getUnidadesJugador());
        assertNotNull(partida.getUnidadesIA());
        assertTrue(partida.isTurnoJugador());
        assertEquals(0, partida.getTurnos());
        assertEquals(FILAS, partida.getTablero().getFilas());
        assertEquals(COLUMNAS, partida.getTablero().getColumnas());
    }

    @Test

    void testInicializar() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        //Veamos primero que se han creado las 2 unidades necesarias
        assertEquals(2, partida.getUnidadesJugador().getNumElementos());
        //Lo mismo con la iA
        assertEquals(2, partida.getUnidadesIA().getNumElementos());
        //Vemos que las posiciones son correctas
        Unidad centralNuclear = partida.getUnidadesJugador().getElemento(0);
        Unidad centralEolica = partida.getUnidadesJugador().getElemento(1);
        assertEquals("Central Nuclear", centralNuclear.getNombre());
        assertEquals("Central Eólica", centralEolica.getNombre());
        assertNotNull(partida.getTablero().getCasilla(0, 0).getUnidad());
        assertNotNull(partida.getTablero().getCasilla(0, COLUMNAS - 1).getUnidad());
        assertNotNull(partida.getTablero().getCasilla(FILAS - 1, 0).getUnidad());
        assertNotNull(partida.getTablero().getCasilla(FILAS - 1, COLUMNAS - 1).getUnidad());
    }
    @Test
    void testTurnoJugadorMovimientoValido() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        Unidad unidad = partida.getUnidadesJugador().getElemento(0);
        CreadorAcciones creador = new CreadorAcciones();
        Accion mover = creador.crearMover();
        assertEquals(0, unidad.getPosicion().getX());
        assertEquals(0, unidad.getPosicion().getY());
        //Intentamos mover(deberiade poderse y cambiar el turno)
        partida.turnoJugador(unidad, mover, 1, 0);
        assertEquals(1, partida.getTurnos());
    }

    @Test
    void testTurnoJugadorAtaqueValido() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        Unidad unidadJugador = partida.getUnidadesJugador().getElemento(0);
        Unidad unidadIA = partida.getUnidadesIA().getElemento(0);
        CreadorAcciones creador = new CreadorAcciones();
        // Colocar unidades cerca para que puedan atacarse, sino nos podemos tirar un rato jejeje
        partida.getTablero().moverUnidad(unidadJugador, 2, 2);
        partida.getTablero().moverUnidad(unidadIA, 2, 3);
        int hpInicialIA = unidadIA.getHp();
        Accion atacar = creador.crearAtacar();
        partida.turnoJugador(unidadJugador, atacar, 2, 3);
        // Verificar que el HP de la unidad IA disminuyo o se mantuvo igual (por el factor aleatorio)
        assertTrue(unidadIA.getHp() <= hpInicialIA);
    }
    @Test
    void testNoPermiteAccionSiNoEsTurnoJugador() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        partida.setTurnoJugador(false);
        Unidad unidad = partida.getUnidadesJugador().getElemento(0);
        CreadorAcciones creador = new CreadorAcciones();
        Accion mover = creador.crearMover();
        Posicion posicionInicial = new Posicion(unidad.getPosicion().getX(), unidad.getPosicion().getY());
        partida.turnoJugador(unidad, mover, 1, 0);
        // Verificar que, efectivamente, no s emovio
        assertEquals(posicionInicial.getX(), unidad.getPosicion().getX());
        assertEquals(posicionInicial.getY(), unidad.getPosicion().getY());
    }

    @Test
    void testNoPermiteUsarUnidadQueNoEsDelJugador() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        Unidad unidadIA = partida.getUnidadesIA().getElemento(0);
        CreadorAcciones creador = new CreadorAcciones();
        Accion mover = creador.crearMover();
        Posicion posicionInicial = new Posicion(unidadIA.getPosicion().getX(), unidadIA.getPosicion().getY());
        partida.turnoJugador(unidadIA, mover, FILAS - 2, 0);
        //De nuevo, vemos que no se ha movido...
        assertEquals(posicionInicial.getX(), unidadIA.getPosicion().getX());
        assertEquals(posicionInicial.getY(), unidadIA.getPosicion().getY());
        assertTrue(partida.isTurnoJugador()); // El turno no debe cambiar
    }
    @Test
    void testTuroIA() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        partida.setTurnoJugador(false);
        int turnosIniciales = partida.getTurnos();
        partida.turnoIA();
        assertTrue(partida.isTurnoJugador());
        assertEquals(turnosIniciales + 1, partida.getTurnos());
    }

    @Test
    void testTurnoIANoEjecutaSiEsTurnoJuador() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        assertTrue(partida.isTurnoJugador());
        int turnosIniciales = partida.getTurnos();
        partida.turnoIA();
        // Verificar que no ha cambiad
        assertTrue(partida.isTurnoJugador());
        assertEquals(turnosIniciales, partida.getTurnos());
    }

    @Test
    void testJuegoterminadoSinUnidadesJugador() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        partida.getUnidadesJugador().clear();
        assertTrue(partida.juegoTerminado());
        assertEquals("CYBER", partida.obtenerGanador());
    }
//Ahora en el caso contrario...
    @Test
    void testJuegoTerminadoSinUnidadesIA() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        // Eliminar todas las unidades IA
        partida.getUnidadesIA().clear();
        assertTrue(partida.juegoTerminado());
        assertEquals("ENERGIA", partida.obtenerGanador());
    }
    @Test
    void testIncrementarTurno() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        int turnosIniciales = partida.getTurnos();
        partida.incrementarTurno();
        assertEquals(turnosIniciales + 1, partida.getTurnos());
    }

    @Test
    void testGeneraNuevaUnidadSegunFrecuencia() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        // Crear partida con frecuencia 2 para test mas facil
        Partida partidaTest = new Partida(6, 6, 2);
        partidaTest.inicializar();
        int unidadesJugadorIniciales = partidaTest.getUnidadesJugador().getNumElementos();
        int unidadesIAIniciales = partidaTest.getUnidadesIA().getNumElementos();
        partidaTest.incrementarTurno();
        partidaTest.incrementarTurno(); //Aqui ya deberia de crearse la nueva unidad
        //Comprobemoslo:
        assertTrue(partidaTest.getUnidadesJugador().getNumElementos() >= unidadesJugadorIniciales);
        assertTrue(partidaTest.getUnidadesIA().getNumElementos() >= unidadesIAIniciales);
    }

    @Test
    void testEvitaFuegoAmigo() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();

        Unidad unidad1 = partida.getUnidadesJugador().getElemento(0);
        Unidad unidad2 = partida.getUnidadesJugador().getElemento(1);

        //Colocar unidades cerca
        partida.getTablero().moverUnidad(unidad1, 2, 2);
        partida.getTablero().moverUnidad(unidad2, 2, 3);

        int hpInicial = unidad2.getHp();
        CreadorAcciones creador = new CreadorAcciones();
        Accion atacar = creador.crearAtacar();

        partida.turnoJugador(unidad1, atacar, 2, 3);

        //Veamos que, efectivamnete, no hubo fuego amigo
        assertEquals(hpInicial, unidad2.getHp());
    }

    @Test
    void testGetters() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partida.inicializar();
        assertNotNull(partida.getTablero());
        assertNotNull(partida.getUnidadesJugador());
        assertNotNull(partida.getUnidadesIA());
        assertTrue(partida.isTurnoJugador());
        assertEquals(0, partida.getTurnos());
        partida.setTurnoJugador(false);
        assertFalse(partida.isTurnoJugador());
    }

}