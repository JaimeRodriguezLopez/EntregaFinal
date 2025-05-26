package Excepciones;

public class CasillaOcupadaException extends Exception {
    private int x, y;
    private String unidadOcupante;

    public CasillaOcupadaException(int x, int y, String unidadOcupante) {
        super("La casilla (" + x + "," + y + ") está ocupada por: " + unidadOcupante);
        this.x = x;
        this.y = y;
        this.unidadOcupante = unidadOcupante;
    }
}
