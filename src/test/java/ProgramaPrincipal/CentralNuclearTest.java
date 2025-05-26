package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CentralNuclearTest {
    @Test
    void testHabilidad() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear central = new CentralNuclear();
        Unidad objetivo = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(central, 0, 0);
        tablero.colocarUnidad(objetivo, 1, 1);
        int hpCentralOriginal = central.getHp();
        int usosOriginal = central.getMaximoUsoHabilidad();
        central.habilidad(objetivo, tablero);
        assertTrue(central.getHp() < hpCentralOriginal);
        assertEquals(usosOriginal - 1, central.getMaximoUsoHabilidad());
    }

    @Test
    void testHabilidadSinUsos() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        //Creamos las mismas condiciones que antes:
        CentralNuclear central = new CentralNuclear();
        Unidad objetivo = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(central, 0, 0);
        tablero.colocarUnidad(objetivo, 1, 1);
        central.setMaximoUsoHabilidad(0);
        int hpObjetivoOriginal = objetivo.getHp();
        central.habilidad(objetivo, tablero);
        assertEquals(hpObjetivoOriginal, objetivo.getHp());
    }

    @Test
    void testHabilidadNoMataCentral() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear central = new CentralNuclear();
        Unidad objetivo = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(central, 0, 0);
        tablero.colocarUnidad(objetivo, 1, 1);
        central.setHp(3);
        int hpAntes = central.getHp();
        central.habilidad(objetivo, tablero);
        assertTrue(central.getHp() > 0);
    }
}
