package ProgramaPrincipal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadDataTest {
    @Test
    void testConstructor() {
        CentralNuclear central = new CentralNuclear();
        central.setPosicion(new Posicion(2, 3));
        central.setHp(100);
        UnidadData unidadData = new UnidadData(central);
        assertEquals("Central Nuclear", unidadData.getNombre());
        assertEquals(100, unidadData.getHp());
        assertEquals(40, unidadData.getAtaque());
        assertEquals(30, unidadData.getDefensa());
        assertEquals(2, unidadData.getRangoMovimiento());
        assertEquals(3, unidadData.getRangoAtaque());
        assertEquals(2, unidadData.getMaximoUsoHabilidad());
        assertEquals(2, unidadData.getPosicionX());
        assertEquals(3, unidadData.getPosicionY());
        assertTrue(unidadData.isEsEnergia());
    }

    @Test
    void testCrearUnidadCentralNuclear() {
        //Probamos con una central nuclea a ver si se guarda bien
        CentralNuclear central = new CentralNuclear();
        central.setPosicion(new Posicion(2, 3));
        central.setHp(100);
        UnidadData unidadData = new UnidadData(central);
        Unidad unidad = unidadData.crearUnidad();
        assertNotNull(unidad);
        assertTrue(unidad instanceof CentralNuclear);
        assertEquals("Central Nuclear", unidad.getNombre());
        assertEquals(100, unidad.getHp());
        assertEquals(2, unidad.getPosicion().getX());
        assertEquals(3, unidad.getPosicion().getY());
    }

    @Test
    void testCrearUnidadMalware() {
        CentralNuclear central = new CentralNuclear();
        central.setPosicion(new Posicion(2, 3));
        central.setHp(100);
        UnidadData unidadData = new UnidadData(central);
        Malware malware = new Malware();
        malware.setPosicion(new Posicion(1, 1));
        UnidadData data = new UnidadData(malware);
        Unidad unidad = data.crearUnidad();
        assertNotNull(unidad);
        assertTrue(unidad instanceof Malware);
        assertEquals("Malware", unidad.getNombre());
    }

    @Test
    void testCrearUnidadTodosLosTipos() {
        //Usamos el metodo de la clase en si para testear todos los tipos:
        CentralNuclear central = new CentralNuclear();
        central.setPosicion(new Posicion(2, 3));
        central.setHp(100);
        UnidadData unidadData = new UnidadData(central);
        // Test para cada tipo de unidad
        String[] nombres = {"Central Nuclear", "Central Eólica", "Central Hidroeléctrica",
                "Malware", "Ransomware", "Phishing"};

        for (String nombre : nombres) {
            if(nombre.equals("Central Nuclear")) {
                UnidadData unidad = new UnidadData(new CentralNuclear());
                assertEquals(nombre, unidad.crearUnidad().getNombre());
            }
            else if(nombre.equals("Central Eólica")) {
                UnidadData unidad = new UnidadData(new CentralEolica());
                assertEquals(nombre, unidad.crearUnidad().getNombre());
            }
            else if(nombre.equals("Central Hidroélectrica")) {
                UnidadData unidad = new UnidadData(new CentralHidroelectrica());
                assertEquals(nombre, unidad.crearUnidad().getNombre());
            }
            else if(nombre.equals("Malware")) {
                UnidadData unidad = new UnidadData(new Malware());
                assertEquals(nombre, unidad.crearUnidad().getNombre());
            }
            else if(nombre.equals("Ransomware")) {
                UnidadData unidad = new UnidadData(new Ransomware());
                assertEquals(nombre, unidad.crearUnidad().getNombre());
            }
            else if(nombre.equals("Phishing")) {
                UnidadData unidad = new UnidadData(new Phishing());
                assertEquals(nombre, unidad.crearUnidad().getNombre());
            }
        }
    }

    @Test
    void testSettersYGetters() {
        CentralNuclear central = new CentralNuclear();
        central.setPosicion(new Posicion(2, 3));
        central.setHp(100);
        UnidadData unidadData = new UnidadData(central);
        unidadData.setNombre("Test");
        unidadData.setHp(50);
        unidadData.setAtaque(25);
        unidadData.setDefensa(15);
        unidadData.setRangoMovimiento(3);
        unidadData.setRangoAtaque(2);
        unidadData.setMaximoUsoHabilidad(1);
        unidadData.setPosicionX(5);
        unidadData.setPosicionY(6);
        unidadData.setEsEnergia(false);

        assertEquals("Test", unidadData.getNombre());
        assertEquals(50, unidadData.getHp());
        assertEquals(25, unidadData.getAtaque());
        assertEquals(15, unidadData.getDefensa());
        assertEquals(3, unidadData.getRangoMovimiento());
        assertEquals(2, unidadData.getRangoAtaque());
        assertEquals(1, unidadData.getMaximoUsoHabilidad());
        assertEquals(5, unidadData.getPosicionX());
        assertEquals(6, unidadData.getPosicionY());
        assertFalse(unidadData.isEsEnergia());
    }

}