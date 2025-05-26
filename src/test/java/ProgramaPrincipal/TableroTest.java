package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    void testConstructor() {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        assertEquals(5, tablero.getFilas());
        assertEquals(5, tablero.getColumnas());
        assertNotNull(tablero.getCasillas());
    }

    @Test
    void testConstructorConCasillas() {
        Casilla[][] casillas = new Casilla[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                casillas[i][j] = new Casilla(1, 0);
            }
        }

        Tablero tableroPersonalizado = new Tablero(3, 3, casillas);
        assertEquals(3, tableroPersonalizado.getFilas());
        assertEquals(3, tableroPersonalizado.getColumnas());
    }

    @Test
    void testGetCasilla() {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        Casilla casilla = tablero.getCasilla(0, 0);
        assertNotNull(casilla);
        Casilla casillaInvalida = tablero.getCasilla(-1, 0);
        assertEquals(null,casillaInvalida);
        casillaInvalida = tablero.getCasilla(10, 0);
        assertEquals(null,casillaInvalida);
    }

    @Test
    void testPosicionValida() {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        assertTrue(tablero.posicionValida(0, 0));
        assertTrue(tablero.posicionValida(4, 4));
        assertFalse(tablero.posicionValida(-1, 0));
        assertFalse(tablero.posicionValida(0, -1));
        assertFalse(tablero.posicionValida(5, 0));
        assertFalse(tablero.posicionValida(0, 5));
    }

    @Test
    void testColocarUnidad() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        assertTrue(tablero.colocarUnidad(unidad1, 0, 0));
        assertEquals(unidad1, tablero.getCasilla(0, 0).getUnidad());
        assertEquals(0, unidad1.getPosicion().getX());
        assertEquals(0, unidad1.getPosicion().getY());
    }

    @Test
    void testColocarUnidadPosicionOcupada() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        tablero.colocarUnidad(unidad1, 0, 0);
        assertFalse(tablero.colocarUnidad(unidad2, 0, 0));

    }

    @Test
    void testColocarUnidadFueraDeTablero() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        assertFalse(tablero.colocarUnidad(unidad1, -1, 0));
        assertFalse(tablero.colocarUnidad(unidad1, 10, 0));
    }

    @Test
    void testMoverUnidad() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        tablero.colocarUnidad(unidad1, 0, 0);
        assertTrue(tablero.moverUnidad(unidad1, 1, 0));
        assertNull(tablero.getCasilla(0, 0).getUnidad());
        assertEquals(unidad1, tablero.getCasilla(1, 0).getUnidad());
        assertEquals(1, unidad1.getPosicion().getX());
        assertEquals(0, unidad1.getPosicion().getY());

    }

    @Test
    void testMoverUnidadSinPosicion() {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        assertFalse(tablero.moverUnidad(unidad1, 1, 0));
    }

    @Test
    void testMoverUnidadPosicionOcupada() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 0);
        assertFalse(tablero.moverUnidad(unidad1, 1, 0));
    }

    @Test
    void testMoverUnidadFueraDeTablero() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        tablero.colocarUnidad(unidad1, 0, 0);
        assertFalse(tablero.moverUnidad(unidad1, -1, 0));
        assertFalse(tablero.moverUnidad(unidad1, 10, 0));

    }

    @Test
    void testObtenerTodasLasUnidades() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Tablero tablero = new Tablero(5, 5);
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 1);
        var unidades = tablero.obtenerTodasLasUnidades();
        assertEquals(2, unidades.getNumElementos());
        assertTrue(unidades.contieneElemento(unidad1));
        assertTrue(unidades.contieneElemento(unidad2));

    }

}