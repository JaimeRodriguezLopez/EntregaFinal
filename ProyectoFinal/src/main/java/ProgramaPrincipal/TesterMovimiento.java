package ProgramaPrincipal;
import ProgramaPrincipal.Unidades.*;

public class TesterMovimiento {
    public static void main(String[] args) {
        Tablero tablero = new Tablero(5, 5);

        Unidad miUnidad = new Eolica() {};

        // Colocar la unidad en la posición (2, 2)
        NodoTablero nodoInicial = tablero.getNodoTablero(2, 2);
        nodoInicial.setUnidadEnCasilla(miUnidad);

        System.out.println("Unidad colocada en: (" + miUnidad.getCoordX() + ", " + miUnidad.getCoordY() + ")");


        tablero.moverNorte(miUnidad);
        System.out.println("Después de mover al norte: (" + miUnidad.getCoordX() + ", " + miUnidad.getCoordY() + ")");
        System.out.println("¿Casilla (2,3) ocupada? " + tablero.getNodoTablero(2, 3).getOcupado());


        tablero.moverSur(miUnidad);
        System.out.println("Después de mover al sur: (" + miUnidad.getCoordX() + ", " + miUnidad.getCoordY() + ")");
        System.out.println("¿Casilla (2,2) ocupada? " + tablero.getNodoTablero(2, 2).getOcupado());


        miUnidad.setCoordX(1);
        miUnidad.setCoordY(0);
        tablero.getNodoTablero(1, 0).setUnidadEnCasilla(miUnidad);
        tablero.moverSur(miUnidad);
        System.out.println("Después de intentar mover al sur desde borde inferior: (" + miUnidad.getCoordX() + ", " + miUnidad.getCoordY() + ")");
    }
}
