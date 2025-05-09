package ProgramaPrincipal;

public class TesterAtaque {
    public static void main(String[] args) {
        Tablero tablero = new Tablero(5, 5);

        // 1. ✅ ATAQUE VÁLIDO - OBJETIVO SOBREVIVE
        Unidad guerrero = new Unidad("Guerrero", 100, 30, 10, 2, 1) {};
        Unidad arquero = new Unidad("Arquero", 70, 25, 5, 3, 2) {};
        tablero.getNodoTablero(2, 2).setUnidadEnCasilla(guerrero);
        tablero.getNodoTablero(2, 3).setUnidadEnCasilla(arquero);
        System.out.println("\n[CASO 1] Guerrero ataca al Arquero (dentro del rango)");

        tablero.unidadAtaca(guerrero, arquero);
        System.out.println("HP del Arquero: " + arquero.hp);
        System.out.println("¿Arquero sigue vivo? " + arquero.getVivo());

        // 2. ☠️ ATAQUE VÁLIDO - OBJETIVO MUERE
        Unidad asesino = new Unidad("Asesino", 50, 100, 0, 3, 1) {}; // daño brutal
        Unidad aldeano = new Unidad("Aldeano", 20, 5, 2, 1, 1) {};
        tablero.getNodoTablero(0, 0).setUnidadEnCasilla(asesino);
        tablero.getNodoTablero(0, 1).setUnidadEnCasilla(aldeano);
        System.out.println("\n[CASO 2] Asesino ataca al Aldeano (lo mata)");

        tablero.unidadAtaca(asesino, aldeano);
        System.out.println("HP del Aldeano: " + aldeano.hp);
        System.out.println("¿Aldeano sigue vivo? " + aldeano.getVivo());

        // 3. ❌ ATAQUE FALLIDO - FUERA DE RANGO
        Unidad caballero = new Unidad("Caballero", 150, 40, 20, 1, 1) {};
        Unidad mago = new Unidad("Mago", 60, 30, 10, 2, 1) {};
        tablero.getNodoTablero(4, 4).setUnidadEnCasilla(caballero);
        tablero.getNodoTablero(0, 0).setUnidadEnCasilla(mago);
        System.out.println("\n[CASO 3] Caballero intenta atacar al Mago (fuera de rango)");

        tablero.unidadAtaca(caballero, mago);
        System.out.println("HP del Mago: " + mago.hp);
        System.out.println("¿Mago sigue vivo? " + mago.getVivo());
    }
}
