package Excepciones;

public class  MovimientoFueraDelTableroException extends Exception {
    private int x, y;
    private int maxFilas, maxColumnas;

    public MovimientoFueraDelTableroException(int x, int y, int maxFilas, int maxColumnas) {
        super("Intento de movimiento fuera del tablero: posición (" + x + "," + y +
                ") no válida. Límites del tablero: " + maxFilas + "x" + maxColumnas);
        this.x = x;
        this.y = y;
        this.maxFilas = maxFilas;
        this.maxColumnas = maxColumnas;
    }
}
