package ProgramaPrincipal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CasillaDataTest {
    private Casilla casilla = new Casilla(3, 15);
    private CasillaData casillaData = new CasillaData(casilla);
    private Unidad unidad = new CentralEolica();;

    @Test
    void testConstructores() {
        //Si es vacio:
        CasillaData empty = new CasillaData();
        assertNotEquals(null,empty); //Deberia de estar definida aunque vacia
        //Sies con casilla:
        assertEquals(3, casillaData.getCostoMovimiento());
        assertEquals(15, casillaData.getModificadorDefensa());
        casilla.setUnidad(unidad);
        CasillaData casillaDataConUnidad = new CasillaData(casilla);
        assertTrue(casillaDataConUnidad.isOcupada());
        assertNotEquals(null,casillaDataConUnidad.getUnidadData());
        //Si es con casilla pero esta esta vacia:
        Casilla casillaVacia = new Casilla(1, 5);
        CasillaData data = new CasillaData(casillaVacia);
        assertEquals(1, data.getCostoMovimiento());
        assertEquals(5, data.getModificadorDefensa());
        assertFalse(data.isOcupada());
        assertNull(data.getUnidadData());
    }

    @Test
    void testDeSetersyGeters() {
        casillaData.setCostoMovimiento(5);
        casillaData.setModificadorDefensa(20);
        casillaData.setOcupada(false);

        assertEquals(5, casillaData.getCostoMovimiento());
        assertEquals(20, casillaData.getModificadorDefensa());
        assertFalse(casillaData.isOcupada());
    }
}