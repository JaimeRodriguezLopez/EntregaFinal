
public class EstadoJuego {
    private int filas;
    private int columnas;
    private CasillaData[][] casillasData;
    private UnidadData[] unidadesJugador;
    private UnidadData[] unidadesIA;
    private boolean turnoJugador;
    private int turnos;
    private int frecuenciaNuevaUnidad;

    // Constructors, getters y setters
    public EstadoJuego() {}

    public EstadoJuego(Partida partida) {
        Tablero tablero = partida.getTablero();
        this.filas = tablero.getFilas();
        this.columnas = tablero.getColumnas();
        this.turnoJugador = partida.isTurnoJugador();
        this.turnos = partida.getTurnos();

        // Convertir casillas
        this.casillasData = new CasillaData[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = tablero.getCasilla(i, j);
                this.casillasData[i][j] = new CasillaData(casilla);
            }
        }

        // Convertir unidades del jugador
        this.unidadesJugador = partida.getUnidadesJugador().stream()
                .map(UnidadData::new)
                .toArray(UnidadData[]::new);

        // Convertir unidades IA
        this.unidadesIA = partida.getUnidadesIA().stream()
                .map(UnidadData::new)
                .toArray(UnidadData[]::new);
    }

    // Getters y setters
    public int getFilas() { return filas; }
    public void setFilas(int filas) { this.filas = filas; }

    public int getColumnas() { return columnas; }
    public void setColumnas(int columnas) { this.columnas = columnas; }

    public CasillaData[][] getCasillasData() { return casillasData; }
    public void setCasillasData(CasillaData[][] casillasData) { this.casillasData = casillasData; }

    public UnidadData[] getUnidadesJugador() { return unidadesJugador; }
    public void setUnidadesJugador(UnidadData[] unidadesJugador) { this.unidadesJugador = unidadesJugador; }

    public UnidadData[] getUnidadesIA() { return unidadesIA; }
    public void setUnidadesIA(UnidadData[] unidadesIA) { this.unidadesIA = unidadesIA; }

    public boolean isTurnoJugador() { return turnoJugador; }
    public void setTurnoJugador(boolean turnoJugador) { this.turnoJugador = turnoJugador; }

    public int getTurnos() { return turnos; }
    public void setTurnos(int turnos) { this.turnos = turnos; }

    public int getFrecuenciaNuevaUnidad() { return frecuenciaNuevaUnidad; }
    public void setFrecuenciaNuevaUnidad(int frecuenciaNuevaUnidad) { this.frecuenciaNuevaUnidad = frecuenciaNuevaUnidad; }
}
