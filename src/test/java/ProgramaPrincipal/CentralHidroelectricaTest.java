package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CentralHidroelectricaTest {

    @Test
    void testConstructor() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralHidroelectrica central = new CentralHidroelectrica();
        Tablero tablero = new Tablero(5, 5);
        Malware malware = new Malware();
        Ransomware ransomware = new Ransomware();
        tablero.colocarUnidad(central, 2, 2);
        tablero.colocarUnidad(malware, 0, 0);
        tablero.colocarUnidad(ransomware, 4, 4);
        assertEquals("Central Hidroeléctrica", central.getNombre());
        assertEquals(120, central.getHp());
        assertEquals(30, central.getAtaque());
        assertEquals(25, central.getDefensa());
        assertEquals(3, central.getRangoMovimiento());
        assertEquals(2, central.getRangoAtaque());
    }

    @Test
    void testHabilidad() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralHidroelectrica central = new CentralHidroelectrica();
        Tablero tablero = new Tablero(5, 5);
        Malware malware = new Malware();
        Ransomware ransomware = new Ransomware();
        tablero.colocarUnidad(central, 2, 2);
        tablero.colocarUnidad(malware, 0, 0);
        tablero.colocarUnidad(ransomware, 4, 4);
        int hpCentralOriginal = central.getHp();
        central.habilidad(malware, tablero);
        assertEquals(hpCentralOriginal, central.getHp()); // La central no se daña
        assertEquals(0, central.getMaximoUsoHabilidad());
    }

}