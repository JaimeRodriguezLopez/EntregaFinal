package ProgramaPrincipal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhishingTest {
    //Ojo, sus hermanos ya estan testeados al 100% en otros tests
    @Test
    void testConstructor() {
        Phishing phishing = new Phishing();
        assertEquals("Phishing", phishing.getNombre());
        assertEquals(80, phishing.getHp());
        assertEquals(30, phishing.getAtaque());
        assertEquals(10, phishing.getDefensa());
        assertEquals(4, phishing.getRangoMovimiento());
        assertEquals(3, phishing.getRangoAtaque());
        assertFalse(phishing.esEnergia());
    }

}