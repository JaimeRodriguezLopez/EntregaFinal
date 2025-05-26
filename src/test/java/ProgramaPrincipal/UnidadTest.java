package ProgramaPrincipal;

import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UnidadTest {
    @Test
    void testSettersYGetters() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 1);
        unidad1.setNombre("Test");
        unidad1.setHp(50);
        unidad1.setAtaque(25);
        unidad1.setDefensa(15);
        unidad1.setRangoMovimiento(3);
        unidad1.setRangoAtaque(2);
        assertEquals("Test", unidad1.getNombre());
        assertEquals(50, unidad1.getHp());
        assertEquals(25, unidad1.getAtaque());
        assertEquals(15, unidad1.getDefensa());
        assertEquals(3, unidad1.getRangoMovimiento());
        assertEquals(2, unidad1.getRangoAtaque());
    }
    @Test
    void atacar() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 1);
        int hpOriginal = unidad2.getHp();
        int dano = unidad1.atacar(unidad2, tablero);
        assertTrue(dano >= 0);
        assertTrue(unidad2.getHp() <= hpOriginal);
    }

    @Test
    void estaMuerta() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 1);
        assertFalse(unidad1.estaMuerta());
        unidad1.setHp(0);
        assertTrue(unidad1.estaMuerta());
        unidad1.setHp(-5);
        assertTrue(unidad1.estaMuerta());
    }

    @Test
    void testToString() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 1);
        String resultado = unidad1.toString();
        assertTrue(resultado.contains("Central Nuclear"));
        assertTrue(resultado.contains("150"));
    }

    @Test
    void EstadosTurnos() throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        CentralNuclear unidad1 = new CentralNuclear();
        Malware unidad2 = new Malware();
        Tablero tablero = new Tablero(5, 5);
        tablero.colocarUnidad(unidad1, 0, 0);
        tablero.colocarUnidad(unidad2, 1, 1);
        assertFalse(unidad1.HaAtacadoEsteTurno());
        assertFalse(unidad1.HaMovidoEsteTurno());
        assertTrue(unidad1.Ataquedisponible());

        unidad1.setHaAtacadoEsteTurno(true);
        unidad1.setHaMovidoEsteTurno(true);
        unidad1.setAtaquedisponible(false);

        assertTrue(unidad1.HaAtacadoEsteTurno());
        assertTrue(unidad1.HaMovidoEsteTurno());
        assertFalse(unidad1.Ataquedisponible());
    }
}