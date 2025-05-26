package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CentralEolicaTest {

    @Test
    void testConstructor() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralEolica central = new CentralEolica();;
        Unidad objetivo = new Malware();;
        Tablero tablero = new Tablero(5, 5);;
        tablero.colocarUnidad(central, 0, 0);
        tablero.colocarUnidad(objetivo, 1, 1);
        assertEquals("Central Eólica", central.getNombre());
        assertEquals(100, central.getHp());
        assertEquals(25, central.getAtaque());
        assertEquals(20, central.getDefensa());
        assertEquals(4, central.getRangoMovimiento());
        assertEquals(2, central.getRangoAtaque());
        assertEquals(1, central.getMaximoUsoHabilidad());
    }

    @Test
    void testHabilidad() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralEolica central = new CentralEolica();;
        Unidad objetivo = new Malware();;
        Tablero tablero = new Tablero(5, 5);;
        tablero.colocarUnidad(central, 0, 0);
        tablero.colocarUnidad(objetivo, 1, 1);
        int rangoMovimientoOriginal = objetivo.getRangoMovimiento();

        central.habilidad(objetivo, tablero);

        assertEquals(rangoMovimientoOriginal - 1, objetivo.getRangoMovimiento());
        assertEquals(0, central.getMaximoUsoHabilidad());
    }

}