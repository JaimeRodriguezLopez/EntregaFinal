package ProgramaPrincipal;

import Estructuras.ListaBasica;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Partida {
    private static final Logger logger = LogManager.getLogger(Partida.class);
    private Tablero tablero;
    private ListaBasica<Unidad> unidadesJugador;
    private ListaBasica<Unidad> unidadesIA;
    private boolean turnoJugador;
    private int turnos;
    private int frecuenciaNuevaUnidad;

    public Partida(int filas, int columnas, int frecuenciaNuevaUnidad) {
        this.tablero = new Tablero(filas, columnas);
        this.unidadesJugador = new ListaBasica<>(filas*columnas);
        this.unidadesIA = new ListaBasica<>(filas*columnas);
        this.turnoJugador = true;
        this.turnos = 0;
        this.frecuenciaNuevaUnidad = frecuenciaNuevaUnidad;
    }

    public void inicializar() {
        logger.info("Iniciando nueva partida - Tablero: " + tablero.getFilas() + "x" + tablero.getColumnas());
        //Creamos las unidades para el humano
        CentralNuclear centralNuclear = new CentralNuclear();
        CentralEolica centralEolica = new CentralEolica();

        tablero.colocarUnidad(centralNuclear, 0, 0);
        tablero.colocarUnidad(centralEolica, 0, tablero.getColumnas() - 1);

        unidadesJugador.add(centralNuclear);
        unidadesJugador.add(centralEolica);
        logger.info("Unidades del jugador creadas: Central Nuclear en (0,0), Central Eólica en (0," + (tablero.getColumnas()-1) + ")");

        //Las de la ia
        Malware malware = new Malware();
        Ransomware ransomware = new Ransomware();

        tablero.colocarUnidad(malware, tablero.getFilas() - 1, 0);
        tablero.colocarUnidad(ransomware, tablero.getFilas() - 1, tablero.getColumnas() - 1);

        unidadesIA.add(malware);
        unidadesIA.add(ransomware);
        logger.info("Unidades IA creadas: Malware y Ransomware posicionados");
        logger.info("Partida inicializada correctamente");
    }

    public void turnoJugador(Unidad unidad, Accion accion, int xDestino, int yDestino) {
        logger.info("Inicia el turno del jugador.");
        if (!turnoJugador || !unidadesJugador.contieneElemento(unidad)) {
            logger.warn("No puedes hacer eso!, no es tu turno o la unidad no es tuya");
            return;
        }
        logger.info("Turno jugador - Unidad: " + unidad.getNombre() + ", Acción: " + accion.getNombre() + ", Destino: (" + xDestino + "," + yDestino + ")");
        ejecutarAccion(unidad, accion, xDestino, yDestino);
        incrementarTurno();

    }

    private void ejecutarAccion(Unidad unidad, Accion accion, int xDestino, int yDestino) {
        if (accion.esMover()) {
            logger.debug("Ejecutando movimiento de " + unidad.getNombre() + " hacia (" + xDestino + "," + yDestino + ")");
            boolean Sepudo = tablero.moverUnidad(unidad, xDestino, yDestino);
            if(Sepudo){
                logger.info("La unidad: "+ unidad.getNombre() + " se ha movido hacia (" + xDestino + "," + yDestino + ")");
            }
            else{
                logger.warn("No se ha podido mover la unidad: "+ unidad.getNombre() + " hacia (" + xDestino + "," + yDestino + ")");
            }
        } else if (accion.esAtacar()) {
            Casilla casillaDestino = tablero.getCasilla(xDestino, yDestino);
            if (casillaDestino != null && casillaDestino.isOcupada()) {
                Unidad objetivo = casillaDestino.getUnidad();
                //No queremos que haya fuego amigo, por tanto, verificamos que sea enemigo
                if (objetivo.esEnergia() != unidad.esEnergia()) {
                    logger.info("Atacando...: " + unidad.getNombre() + " ataca a " + objetivo.getNombre());
                    Posicion posUnidad = unidad.getPosicion();
                    int distancia = Math.abs(posUnidad.getX() - xDestino) +
                            Math.abs(posUnidad.getY() - yDestino);
                    if (distancia <= unidad.getRangoAtaque()) {
                        int dano = unidad.atacar(objetivo,tablero);
                        logger.info("Se ha atacado correctamente: " + objetivo.getNombre() + ", causando: "+ dano + " de daño.");
                        if (objetivo.estaMuerta()) {
                            logger.info("La unidad: "+ objetivo.getNombre() + " ha muerto a causa del ataque");
                            eliminarUnidad(objetivo);
                        }
                    }
                    else{
                        logger.warn("Ojo!, no puedes atacar a ese objetivo porque está fuera del rango de ataque");
                    }
                }
                else{
                    logger.warn("Se ha intentado atacar a una unidad amiga, lo hemos evitado correctamente pero pierdes tu turno");
                }
            }
        }
    }

    public void turnoIA() {
        if (turnoJugador) {
            return;
        }
        logger.info("Iniciando el turno de nuestra IA");
        int i = (int) (Math.random() * unidadesIA.getNumElementos()); //Asi, en cada turno una unidad aleatoria sera la que se mueva
        Unidad tmp = unidadesIA.getElemento(i);//Tmp es la unidad que va a usar la ia
        Unidad objetivo = encontrarObjetivoMasCercano(tmp);

        if (objetivo != null) {
            Posicion posUnidad = tmp.getPosicion();
            Posicion posObjetivo = objetivo.getPosicion();

            int distancia = Math.abs(posUnidad.getX() - posObjetivo.getX()) + Math.abs(posUnidad.getY() - posObjetivo.getY());

            if (distancia <= tmp.getRangoAtaque()) { //Hemos establecido que la prioridad para la ia es que ataque si puede
                int dano = tmp.atacar(objetivo,tablero);
                logger.info("La IA ha decidido que: "+tmp.getNombre()+ " ataque a : " + objetivo.getNombre() + "y le ha causado: "+ dano + " de daño.");

                if (objetivo.estaMuerta()) {
                    logger.warn("La IA ha matado a : " + objetivo.getNombre());
                    eliminarUnidad(objetivo);
                }
            } else {
                // Mover hacia el objetivo
                int xActual = posUnidad.getX();
                int yActual = posUnidad.getY();
                int xObjetivo = posObjetivo.getX();
                int yObjetivo = posObjetivo.getY();
                int direccx = 0;
                int direccy = 0;
                if (xActual < xObjetivo) {
                    direccx = 1; //Para que se mueva a la dcha si el enemigo esta mas a la dcha;
                }
                else if (xActual > xObjetivo) {
                    direccx = -1; //A la izq...
                }
                if (yActual < yObjetivo) {
                    direccy = 1; // para que baje si esta abajo el objetivo;
                }
                else if (yActual > yObjetivo) {
                    direccy = -1;//Sube..
                }

                if (Math.abs(xObjetivo - xActual) > Math.abs(yObjetivo - yActual)) { //Vemos cual de las dos distancias es mayor y nos movemos con respecto a esa
                    if (!tablero.moverUnidad(tmp, xActual + direccx, yActual)) {
                        tablero.moverUnidad(tmp, xActual, yActual + direccy); //Intentamos moverlo en horizontal, si no se puede por estar ocupada, que se mueva en vertical.
                    }
                } else {
                    if (!tablero.moverUnidad(tmp, xActual, yActual + direccy)) {
                        tablero.moverUnidad(tmp, xActual + direccx, yActual);
                    }
                }
                logger.info("La IA ha decidido que: "+tmp.getNombre() + " se mueva hacia: (" + tmp.getPosicion().getX() + ", " + tmp.getPosicion().getY() + ")");
            }
        }
        turnoJugador = true;
        incrementarTurno();
    }

    private Unidad encontrarObjetivoMasCercano(Unidad unidad) {
        Posicion posUnidad = unidad.getPosicion();
        Unidad unidadMasCercana = null;
        int distanciaMinima = getTablero().getFilas()*getTablero().getColumnas()*10;
        //Ahora, buscamos cual es el bando contrario
        ListaBasica<Unidad> objetivos;
        if (unidad.esEnergia()) {
            objetivos = unidadesIA;
        } else {
            objetivos = unidadesJugador;
        }
        for (int i=0; i< objetivos.getNumElementos(); i++){
            Unidad objetivo = objetivos.getElemento(i);
            Posicion posObjetivo = objetivo.getPosicion();
            int distancia = Math.abs(posUnidad.getX() - posObjetivo.getX()) +
                    Math.abs(posUnidad.getY() - posObjetivo.getY());

            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                unidadMasCercana = objetivo;
            }
        }

        return unidadMasCercana;
    }

    private void eliminarUnidad(Unidad unidad) {
        Posicion pos = unidad.getPosicion();
        logger.info("Eliminando la unidad del juego: " + unidad.getNombre() + " en la posicion (" + pos.getX() + "," + pos.getY() + ")");
        tablero.getCasilla(pos.getX(), pos.getY()).setUnidad(null);
        if (unidad.esEnergia()) {
            logger.info("Unidad del jugador eliminada. Unidades restantes: " + unidadesJugador.getNumElementos());
            unidadesJugador.delete(unidad);
        } else {
            logger.info("Unidad de la ia eliminada. Unidades restantes: " + unidadesIA.getNumElementos());
            unidadesIA.delete(unidad);
        }
        if (this.juegoTerminado()){
            logger.warn("Juego terminado, el ganador es: " + obtenerGanador() + "!!!");
        }
    }

    private void generarNuevaUnidad() {

        if (!unidadesJugador.isEmpty()) {
            Unidad nuevaUnidad;
            int tipo = (int) (Math.random() * 3);
            if (tipo == 0) {
                nuevaUnidad = new CentralNuclear();
            } else if (tipo == 1) {
                nuevaUnidad = new CentralEolica();
            } else {
                nuevaUnidad = new CentralHidroelectrica();
            }
            colocarNuevaUnidad(nuevaUnidad, unidadesJugador);
        }

        if (!unidadesIA.isEmpty()) {
            Unidad nuevaUnidad;
            int tipo = (int) (Math.random() * 3);
            if (tipo == 0) {
                nuevaUnidad = new Malware();
            } else if (tipo == 1) {
                nuevaUnidad = new Ransomware();
            } else {
                nuevaUnidad = new Phishing();
            }
            colocarNuevaUnidad(nuevaUnidad, unidadesIA);
        }
    }

    private void colocarNuevaUnidad(Unidad nuevaUnidad, ListaBasica<Unidad> listaUnidades) {
        ListaBasica<int[]> posicionesLibres = new ListaBasica<>(tablero.getColumnas()*tablero.getFilas());
        //Aqui, recorremos el tablero entero y metemos la posicion a nuestra lista en caso de que este libre
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (!tablero.getCasilla(i, j).isOcupada()) {
                    posicionesLibres.add(new int[]{i, j});
                }
            }
        }
        if (posicionesLibres.isEmpty()) {
            logger.warn("No hay casillas libres para colocar la unidad: " + nuevaUnidad.getNombre());
            return;
        }

        // Hemos planteado que las unidades se coloquen en sitios aleatorios
        int indiceAleatorio = (int) (Math.random() * posicionesLibres.getNumElementos());
        int[] posicionElegida = posicionesLibres.getElemento(indiceAleatorio);
        int x = posicionElegida[0];
        int y = posicionElegida[1];
        //Ya solo nos queda colocar la unidad en la posicion que ha surgido
        if (tablero.colocarUnidad(nuevaUnidad, x, y)) {
            listaUnidades.add(nuevaUnidad);
            logger.info("La unidad: " + nuevaUnidad.getNombre() + " se colocó en la posición (" + x + ", " + y + ")");
        } else {
            logger.warn("Error al colocar la unidad: " + nuevaUnidad.getNombre());
        }
    }

    public boolean juegoTerminado(){
        return unidadesJugador.isEmpty() || unidadesIA.isEmpty();
    }

    public String obtenerGanador() {
        if (unidadesIA.isEmpty()) {
            return "ENERGIA";
        } else if (unidadesJugador.isEmpty()) {
            return "CYBER";
        }
        return null;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ListaBasica<Unidad> getUnidadesJugador() {
        return unidadesJugador;
    }

    public ListaBasica<Unidad> getUnidadesIA() {
        return unidadesIA;
    }

    public boolean isTurnoJugador() {
        return turnoJugador;
    }

    public int getTurnos() {
        return turnos;
    }
    public void setTurnoJugador(boolean turnoJugador) {
        this.turnoJugador = turnoJugador;
    }

    public void incrementarTurno() {
        turnos++;
        if (turnos % frecuenciaNuevaUnidad == 0) {
            generarNuevaUnidad();
        }
    }
}