import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorJSON {
    private static final Gson gson = new Gson();

    public static void guardarPartida(Partida partida, String nombreArchivo) throws IOException {
        EstadoJuego estado = new EstadoJuego(partida);

        try (FileWriter writer = new FileWriter(nombreArchivo + ".json")) {
            gson.toJson(estado, writer);
        }
        catch (IOException e) {
            System.out.println("El archivo no existe");
            e.printStackTrace();
        }
    }

    public static Partida cargarPartida(String nombreArchivo) throws IOException {
        EstadoJuego estado;
        try (FileReader reader = new FileReader(nombreArchivo + ".json")) {
            estado = gson.fromJson(reader, EstadoJuego.class);
        }
        return reconstruirPartida(estado);
    }
    private static Partida reconstruirPartida(EstadoJuego estado) {
        Partida partida = new Partida(estado.getFilas(), estado.getColumnas(), estado.getFrecuenciaNuevaUnidad());
        //Primero reconstruimos el tablero
        Tablero tablero = partida.getTablero();
        for (int i = 0; i < estado.getFilas(); i++) {
            for (int j = 0; j < estado.getColumnas(); j++) {
                CasillaData casillaData = estado.getCasillasData()[i][j];
                // Aquí necesitarías un método para establecer la casilla en el tablero
                // o modificar el constructor del tablero para aceptar datos predefinidos
            }
        }

        // Reconstruir unidades del jugador
        partida.getUnidadesJugador().clear();
        for (UnidadData unidadData : estado.getUnidadesJugador()) {
            Unidad unidad = unidadData.crearUnidad();
            partida.getUnidadesJugador().add(unidad);
            tablero.colocarUnidad(unidad, unidadData.getPosicionX(), unidadData.getPosicionY());
        }

        // Reconstruir unidades IA
        partida.getUnidadesIA().clear();
        for (UnidadData unidadData : estado.getUnidadesIA()) {
            Unidad unidad = unidadData.crearUnidad();
            partida.getUnidadesIA().add(unidad);
            tablero.colocarUnidad(unidad, unidadData.getPosicionX(), unidadData.getPosicionY());
        }

        return partida;
    }
}
