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
        EstadoJuego estado = null;
        try (FileReader reader = new FileReader(nombreArchivo + ".json")) {
            estado = gson.fromJson(reader, EstadoJuego.class);
        }
        catch (IOException e) {
            System.out.println("El archivo no existe");
            e.printStackTrace();
        }
        return reconstruirPartida(estado);
    }
    private static Partida reconstruirPartida(EstadoJuego estado) {
        Casilla[][] casillareconstruida = new Casilla[estado.getFilas()][estado.getColumnas()];
        Partida partida = new Partida(estado.getFilas(), estado.getColumnas(), estado.getFrecuenciaNuevaUnidad());
        //Primero reconstruimos el tablero (Ojo, las unidades aun no las metemos, solo las casillas)
        Tablero tablero = partida.getTablero();
        for (int i = 0; i < estado.getFilas(); i++) {
            for (int j = 0; j < estado.getColumnas(); j++) {
                CasillaData casillaData = estado.getCasillasData()[i][j];
                casillareconstruida[i][j] = new Casilla(casillaData.getCostoMovimiento(),casillaData.getModificadorDefensa());
            }
        }

        //Reconstruimos las unidades del jugador
        partida.getUnidadesJugador().clear();
        for (UnidadData unidadData : estado.getUnidadesJugador()) {
            Unidad unidad = unidadData.crearUnidad();
            partida.getUnidadesJugador().add(unidad);
            tablero.colocarUnidad(unidad, unidadData.getPosicionX(), unidadData.getPosicionY());
        }

        //Ahora lo mismo con las ia
        partida.getUnidadesIA().clear();
        for (UnidadData unidadData : estado.getUnidadesIA()) {
            Unidad unidad = unidadData.crearUnidad();
            partida.getUnidadesIA().add(unidad);
            tablero.colocarUnidad(unidad, unidadData.getPosicionX(), unidadData.getPosicionY());
        }

        return partida;
    }
}
