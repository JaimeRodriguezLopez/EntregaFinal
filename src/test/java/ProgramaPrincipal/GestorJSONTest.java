package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GestorJSONTest {
    Partida partidaTest = new Partida(5, 5, 3);
    String nombreArchivoTest = "partidaTest";
    File archivoTest = new File(nombreArchivoTest + ".json");

    @Test
    public void testGuardarPartida() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        partidaTest.inicializar();
        // Test para verificar que se puede guardar una partida
        try {
            GestorJSON.guardarPartida(partidaTest, nombreArchivoTest);

            // Verificar que el archivo se creó
            assertTrue(archivoTest.exists(), "El archivo debería haberse creado");
            assertTrue(archivoTest.length() > 0, "El archivo no debería estar vacío");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCargarPartida() throws IOException, MovimientoFueraDelTableroException, CasillaOcupadaException {
        partidaTest.inicializar();
        // Primero guardamos una partida
        GestorJSON.guardarPartida(partidaTest, nombreArchivoTest);
        //Luego la cargamos
        Partida partidaCargada = GestorJSON.cargarPartida(nombreArchivoTest);
        // Verificaciones básicas
        assertNotNull(partidaCargada, "La partida cargada no debería ser null");
        assertEquals(partidaTest.getTablero().getFilas(), partidaCargada.getTablero().getFilas()); //Las filas deberian de conicidir
        assertEquals(partidaTest.getTablero().getColumnas(),partidaCargada.getTablero().getColumnas()); //Lo mismo con las columnas
        // Verificar que las unidades se cargaron correctamente
        assertEquals(partidaTest.getUnidadesJugador().getNumElementos(), partidaCargada.getUnidadesJugador().getNumElementos());
        assertEquals(partidaTest.getUnidadesIA().getNumElementos(), partidaCargada.getUnidadesIA().getNumElementos());
    }



    @Test
    public void testGuardarCargar() throws IOException, MovimientoFueraDelTableroException, CasillaOcupadaException {
        partidaTest.inicializar();
        // Modificar el estado de la partida
        partidaTest.setTurnoJugador(false);
        //Obtener una unidad y modificar su HP para probar el json
        if (partidaTest.getUnidadesJugador().getNumElementos() > 0) {
            Unidad unidad = partidaTest.getUnidadesJugador().getElemento(0);
            int hpOriginal = unidad.getHp();
            unidad.setHp(hpOriginal - 10);
        }
        //Guardar
        GestorJSON.guardarPartida(partidaTest, nombreArchivoTest);
        //Cargamos
        Partida partidaRecuperada = GestorJSON.cargarPartida(nombreArchivoTest);

        if (partidaRecuperada.getUnidadesJugador().getNumElementos() > 0) {
            Unidad unidadRecuperada = partidaRecuperada.getUnidadesJugador().getElemento(0);
            Unidad unidadOriginal = partidaTest.getUnidadesJugador().getElemento(0);
            assertEquals(unidadOriginal.getHp(), unidadRecuperada.getHp());
        }
    }

    @Test
    public void testPosicionesUnidades() throws IOException, MovimientoFueraDelTableroException, CasillaOcupadaException {
        partidaTest.inicializar();
        GestorJSON.guardarPartida(partidaTest, nombreArchivoTest);
        Partida partidaCargada = GestorJSON.cargarPartida(nombreArchivoTest);
        for (int i = 0; i < partidaTest.getUnidadesJugador().getNumElementos(); i++) {
            Unidad original = partidaTest.getUnidadesJugador().getElemento(i);
            Unidad cargada = partidaCargada.getUnidadesJugador().getElemento(i);
            assertEquals(original.getPosicion().getX(), cargada.getPosicion().getX());
            assertEquals(original.getPosicion().getY(), cargada.getPosicion().getY());
        }

        //Habria que hacer lo mismo para las unidades de la ia

    }

}