package com.example.practicaultima;

import Estructuras.ListaBasica;
import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import ProgramaPrincipal.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class VistaPartidaController {

    @FXML private GridPane gridTablero;
    @FXML private Label labelUnidadSeleccionada;
    @FXML private Label labelTurno;
    @FXML private Label labelHp;
    @FXML private Label labelAtaque;
    @FXML private Label labelDefensa;
    @FXML private Label labelRangoMov;
    @FXML private Label labelRangoAtq;
    @FXML public Button TerminarButton;
    @FXML public Button VolverDesdePartida;
    private ListaBasica<Posicion> casillasMovimiento = new ListaBasica<>(100);
    private ListaBasica<Posicion> casillasAtaque = new ListaBasica<>(100);

    private Partida partida;
    private Unidad unidadSeleccionada = null;
    private boolean modoMover = false;
    private boolean modoAtacar = false;

    public void inicializar(Partida partida) {
        this.partida = partida;
        actualizarVista();
    }
    @FXML
    protected void VolverMenuPrincipalButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) VolverDesdePartida.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void actualizarVista() {//Cambia la vista
        gridTablero.getChildren().clear();
        Tablero tablero = partida.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {//va casilla por casilla
                Casilla casilla = tablero.getCasilla(i, j);
                Button btn = new Button();
                boolean iluminarMovimiento = false;//Esto es por si se llama desde el boton de atacar y de movimiento
                boolean iluminarAtaque = false;


                if (casilla.getUnidad() != null) {
                    btn.setText(casilla.getUnidad().getNombre().substring(0, 2));
                    if (casilla.getUnidad().esEnergia()) {
                        btn.setStyle("-fx-background-color: #00ffff; -fx-text-fill: white;"); // azul Humano
                    } else {
                        btn.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;"); // rojo IA
                    }
                } else if (casilla.getCostoMovimiento() == 2) {
                    btn.setText("M"); // Montaña
                    btn.setStyle("-fx-background-color: #8B4513; -fx-text-fill: white;");
                } else {
                    btn.setText(""); // Normal
                    btn.setStyle("-fx-background-color: #008000; -fx-text-fill: white;");
                }
                for (int k = 0; k < casillasMovimiento.getNumElementos(); k++) {//mira a ver si el nodo esta en la lista de los qeu la unidad se puede mover
                    Posicion p = casillasMovimiento.getElemento(k);
                    if (p.getX() == i && p.getY() == j) {
                        iluminarMovimiento = true;
                    }
                }
                for (int k = 0; k < casillasAtaque.getNumElementos(); k++) {//lo mismo pero atacar
                    Posicion p = casillasAtaque.getElemento(k);
                    if (p.getX() == i && p.getY() == j) {
                        iluminarAtaque = true;
                    }
                }
                if (modoMover && iluminarMovimiento) {
                    btn.setStyle("-fx-background-color: #FFD700; -fx-border-color: #CCCC00; -fx-text-fill: black;");//los pinta
                }
                if (modoAtacar && iluminarAtaque) {
                    btn.setStyle("-fx-background-color: #FFFFF; -fx-border-color: #B22222; -fx-text-fill: white;");//los pinta
                }

                if (unidadSeleccionada != null) {
                    labelHp.setText("HP: " + unidadSeleccionada.getHp());//Actualiza labels
                    labelAtaque.setText("Ataque: " + unidadSeleccionada.getAtaque());
                    labelDefensa.setText("Defensa: " + unidadSeleccionada.getDefensa());
                    labelRangoMov.setText("Rango mov.: " + unidadSeleccionada.getRangoMovimiento());
                    labelRangoAtq.setText("Rango atq.: " + unidadSeleccionada.getRangoAtaque());
                } else {
                    labelHp.setText("HP: ");
                    labelAtaque.setText("Ataque: ");
                    labelDefensa.setText("Defensa: ");
                    labelRangoMov.setText("Rango mov.: ");
                    labelRangoAtq.setText("Rango atq.: ");
                }
                int x = i, y = j;
                btn.setPrefSize(40, 40);
                btn.setOnAction(e -> {
                    try {
                        onCasillaClick(x, y);
                    } catch (MovimientoFueraDelTableroException ex) {
                        throw new RuntimeException(ex);
                    } catch (CasillaOcupadaException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                gridTablero.add(btn, j, i);
            }
        }
        casillasMovimiento.clear();//limpia los array de casillas de movimiento y ataque
        casillasAtaque.clear();
        labelTurno.setText(partida.isTurnoJugador() ? "Turno: Jugador" : "Turno: IA");
        if (unidadSeleccionada != null) {
            labelUnidadSeleccionada.setText("Unidad: " + unidadSeleccionada.getNombre() + " (" + unidadSeleccionada.getHp() + " HP)");
        } else {
            labelUnidadSeleccionada.setText("Unidad: -");
        }
    }

    private void onCasillaClick(int x, int y) throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        Casilla casilla = partida.getTablero().getCasilla(x, y);

        // Si estamos en modo mover
        if (modoMover && unidadSeleccionada != null) {
            unidadSeleccionada.setHaMovidoEsteTurno(true);//para que no se pueda mover mas
            Accion mover = new Accion("MOVER");
            partida.turnoJugador(unidadSeleccionada, mover, x, y);//Mueve la unidad
            modoMover = false;
            unidadSeleccionada = null;
            comprobarFinYActualizar();
            return;
        }

        // Si estamos en modo atacar
        if (modoAtacar && unidadSeleccionada != null) {
            unidadSeleccionada.setHaAtacadoEsteTurno(true);//para que no vuelva a atacar
            Accion atacar = new Accion("ATACAR");
            partida.turnoJugador(unidadSeleccionada, atacar, x, y);
            modoAtacar = false;
            unidadSeleccionada = null;
            comprobarFinYActualizar();
            return;
        }

        if (casilla.getUnidad() != null) {
            unidadSeleccionada = casilla.getUnidad();
            labelUnidadSeleccionada.setText("Unidad: " + unidadSeleccionada.getNombre() + " (" + unidadSeleccionada.getHp() + " HP)");
            // Actualiza los labels de estadísticas
            labelHp.setText("HP: " + unidadSeleccionada.getHp());
            labelAtaque.setText("Ataque: " + unidadSeleccionada.getAtaque());
            labelDefensa.setText("Defensa: " + unidadSeleccionada.getDefensa());
            labelRangoMov.setText("Rango mov.: " + unidadSeleccionada.getRangoMovimiento());
            labelRangoAtq.setText("Rango atq.: " + unidadSeleccionada.getRangoAtaque());
        } else {
            unidadSeleccionada = null;
            labelUnidadSeleccionada.setText("Unidad: -");
            labelHp.setText("HP: ");
            labelAtaque.setText("Ataque: ");
            labelDefensa.setText("Defensa: ");
            labelRangoMov.setText("Rango mov.: ");
            labelRangoAtq.setText("Rango atq.: ");
        }
        actualizarVista();
    }

    @FXML
    private void onMoverClick() {//Eliges moverte

        if (unidadSeleccionada.HaMovidoEsteTurno()){
            mostrarInfo("Esta unidad ya se ha movido");//Ya se ha movido
        } else if (unidadSeleccionada != null && partida.isTurnoJugador() && !unidadSeleccionada.HaMovidoEsteTurno()) {//te indica que te puedes mover
            modoMover = true;
            modoAtacar = false;
            casillasMovimiento = calcularCasillasMovimiento(unidadSeleccionada);
            casillasAtaque.clear();
            actualizarVista();
            mostrarInfo("Selecciona la casilla destino para mover.");
        }
    }

    @FXML
    private void onAtacarClick() {
        if (unidadSeleccionada.HaAtacadoEsteTurno()){//ya has atacado
            mostrarInfo("Esta unidad ya ha atacado");
        } else if (unidadSeleccionada != null && partida.isTurnoJugador()) {//te indica qeu puedes atacar
            modoAtacar = true;
            modoMover = false;
            casillasAtaque = calcularCasillasAtaque(unidadSeleccionada);
            casillasMovimiento.clear();
            actualizarVista();
            mostrarInfo("Selecciona la casilla objetivo para atacar.");
        }

    }

    @FXML
    private void onTerminarTurnoClick() throws MovimientoFueraDelTableroException, CasillaOcupadaException {//pasar turno
        if (!partida.isTurnoJugador()) return;
        casillasMovimiento.clear();
        casillasAtaque.clear();
        // Ahora sí, termina el turno del jugador
        partida.setTurnoJugador(false);
        partida.incrementarTurno();     // sumar turnos y generar unidades

        partida.turnoIA();              // Llama al turno de la IA
        unidadSeleccionada = null;
        for (int i = 0; i < partida.getUnidadesJugador().getNumElementos(); i++) {//reset a haber atacado y movido
            Unidad u = partida.getUnidadesJugador().getElemento(i);
            u.setHaMovidoEsteTurno(false);
            u.setHaAtacadoEsteTurno(false);
        }
        for (int i = 0; i < partida.getUnidadesIA().getNumElementos(); i++) {
            Unidad u = partida.getUnidadesIA().getElemento(i);
            u.setHaMovidoEsteTurno(false);
            u.setHaAtacadoEsteTurno(false);
        }
        comprobarFinYActualizar();
    }

    private void comprobarFinYActualizar() {//condicion de victoria
        if (partida.juegoTerminado()) {
            String ganador = partida.obtenerGanador();
            mostrarInfo("¡Fin de la partida! Ganador: " + ganador);
            // Aquí puedes desactivar la interfaz o volver al menú
        }
        actualizarVista();
    }

    private void mostrarInfo(String msg) {//Error o informaciones
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    @FXML
    private void onGuardarPartidaClick() {
        try {

            LocalDateTime locaDate = LocalDateTime.now();
            int hours  = locaDate.getHour();
            int minutes = locaDate.getMinute();
            int seconds = locaDate.getSecond();
            String intHora = Integer.toString(hours);
            String intMin = Integer.toString(minutes);
            String intSec = Integer.toString(seconds);
            GestorJSON.guardarPartida(partida, "Partidas/partida_guardada"+intHora+","+intMin+","+intSec);//Lo guarda asi para poder guardar varias veces la misma partida sin override
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setHeaderText(null);
            alert.setContentText("Partida guardada correctamente.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo guardar la partida.");
            alert.showAndWait();//Informa si ha funcionado
        }
    }
    @FXML
    private void onHabilidadClick() throws MovimientoFueraDelTableroException, CasillaOcupadaException {//Aun no funciona bien
        if (unidadSeleccionada != null && partida.isTurnoJugador()) {
            // Busca un objetivo válido (por ejemplo, una unidad enemiga en rango)
            Unidad objetivo = buscarObjetivoParaHabilidad(unidadSeleccionada);
            if (objetivo != null) {
                if (unidadSeleccionada instanceof UnidadEnergia) {
                    ((UnidadEnergia) unidadSeleccionada).habilidad(objetivo, partida.getTablero());
                    // Después de usar la habilidad, termina el turno como en un ataque normal
                    partida.turnoJugador(unidadSeleccionada, new Accion("ATACAR"), objetivo.getPosicion().getX(), objetivo.getPosicion().getY());
                    unidadSeleccionada = null;
                    actualizarVista();
                }
            } else {
                mostrarInfo("No hay objetivo válido para la habilidad.");
            }
        } else {
            mostrarInfo("Selecciona una unidad propia para usar la habilidad.");
        }
    }

    private Unidad buscarObjetivoParaHabilidad(Unidad unidad) {
        // Ejemplo: busca la primera unidad enemiga en el tablero
        Tablero tablero = partida.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Casilla casilla = tablero.getCasilla(i, j);
                if (casilla.getUnidad() != null && casilla.getUnidad().esEnergia() != unidad.esEnergia()) {
                    return casilla.getUnidad();
                }
            }
        }
        return null;
    }
    private ListaBasica<Posicion> calcularCasillasMovimiento(Unidad unidad) {//Buscar hasta qeu casilla se puede mover
        ListaBasica<Posicion> resultado = new ListaBasica<>(1000);
        Tablero tablero = partida.getTablero();
        int rango = unidad.getRangoMovimiento();
        Posicion origen = unidad.getPosicion();

        boolean[][] visitado = new boolean[tablero.getFilas()][tablero.getColumnas()];
        int[][] dist = new int[tablero.getFilas()][tablero.getColumnas()];
        for (int i = 0; i < tablero.getFilas(); i++)
            for (int j = 0; j < tablero.getColumnas(); j++)
                dist[i][j] = Integer.MAX_VALUE;

        ListaBasica<Posicion> frontera = new ListaBasica<>(1000);
        frontera.add(origen);
        dist[origen.getX()][origen.getY()] = 0;
        visitado[origen.getX()][origen.getY()] = true;

        while (!frontera.isEmpty()) {
            Posicion actual = frontera.getElemento(0);
            frontera.delete(actual);
            int x = actual.getX();
            int y = actual.getY();
            int d = dist[x][y];

            if (d > 0 && d <= rango && !tablero.getCasilla(x, y).isOcupada()) {
                resultado.add(new Posicion(x, y));
            }

            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int idx = 0; idx < 4; idx++) {
                int nx = x + dirs[idx][0];
                int ny = y + dirs[idx][1];
                if (tablero.posicionValida(nx, ny) && !visitado[nx][ny]) {
                    int nuevoCosto = d + tablero.getCasilla(nx, ny).getCostoMovimiento();

                    if (nuevoCosto <= rango && !tablero.getCasilla(nx, ny).isOcupada()) {
                        frontera.add(new Posicion(nx, ny));
                        dist[nx][ny] = nuevoCosto;
                        visitado[nx][ny] = true;
                    }
                }
            }
        }
        return resultado;
    }


    private ListaBasica<Posicion> calcularCasillasAtaque(Unidad unidad) {//Buscar que casillas en su rango contienen tropas enemigas
        ListaBasica<Posicion> resultado = new ListaBasica<>(1000);
        int rango = unidad.getRangoAtaque();
        Posicion pos = unidad.getPosicion();
        Tablero tablero = partida.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                int dist = Math.abs(i - pos.getX()) + Math.abs(j - pos.getY());
                Casilla c = tablero.getCasilla(i, j);
                if (dist > 0 && dist <= rango && c.getUnidad() != null && c.getUnidad().esEnergia() != unidad.esEnergia()) {
                    resultado.add(new Posicion(i, j));
                }
            }
        }
        return resultado;
    }

}
