package ProgramaPrincipal;

import Estructuras.ListaBasica;


public class Partida {
    private Tablero tablero;
    private ListaBasica<Unidad> unidadesJugador;
    private ListaBasica<Unidad> unidadesIA;
    private boolean turnoJugador;
    private int turnos;
    private int frecuenciaNuevaUnidad;
    private CreadorAcciones creadorAcciones;

    public Partida(int filas, int columnas, int frecuenciaNuevaUnidad) {
        this.tablero = new Tablero(filas, columnas);
        this.unidadesJugador = new ListaBasica<>(filas*columnas);
        this.unidadesIA = new ListaBasica<>(filas*columnas);
        this.turnoJugador = true;
        this.turnos = 0;
        this.frecuenciaNuevaUnidad = frecuenciaNuevaUnidad;
        this.creadorAcciones = new CreadorAcciones();
    }

    public void inicializar() {
        //Creamos las unidades para el humano
        CentralNuclear centralNuclear = new CentralNuclear();
        CentralEolica centralEolica = new CentralEolica();

        tablero.colocarUnidad(centralNuclear, 0, 0);
        tablero.colocarUnidad(centralEolica, 0, tablero.getColumnas() - 1);

        unidadesJugador.add(centralNuclear);
        unidadesJugador.add(centralEolica);

        //Las de la ia
        Malware malware = new Malware();
        Ransomware ransomware = new Ransomware();

        tablero.colocarUnidad(malware, tablero.getFilas() - 1, 0);
        tablero.colocarUnidad(ransomware, tablero.getFilas() - 1, tablero.getColumnas() - 1);

        unidadesIA.add(malware);
        unidadesIA.add(ransomware);
    }

    public void turnoJugador(Unidad unidad, Accion accion, int xDestino, int yDestino) {
        if (!turnoJugador || !unidadesJugador.contieneElemento(unidad)) {
            return;
        }
        ejecutarAccion(unidad, accion, xDestino, yDestino);

    }

    private void ejecutarAccion(Unidad unidad, Accion accion, int xDestino, int yDestino) {
        if (accion.esMover()) {
            tablero.moverUnidad(unidad, xDestino, yDestino);
        } else if (accion.esAtacar()) {
            Casilla casillaDestino = tablero.getCasilla(xDestino, yDestino);
            if (casillaDestino != null && casillaDestino.isOcupada()) {
                Unidad objetivo = casillaDestino.getUnidad();
                //No queremos que haya fuego amigo, por tanto, verificamos que sea enemigo
                if (objetivo.esEnergia() != unidad.esEnergia()) {
                    unidad.atacar(objetivo,tablero);
                    if (objetivo.estaMuerta()) {
                        eliminarUnidad(objetivo);
                    }
                }
            }
        }
    }

    public void turnoIA() {
        if (turnoJugador) {
            return;
        }
        int i = (int) (Math.random() * unidadesIA.getNumElementos()); //Asi, en cada turno una unidad aleatoria sera la que se mueva
        Unidad tmp = unidadesIA.getElemento(i);//Tmp es la unidad que va a usar la ia
        Unidad objetivo = encontrarObjetivoMasCercano(tmp);

        if (objetivo != null) {
            Posicion posUnidad = tmp.getPosicion();
            Posicion posObjetivo = objetivo.getPosicion();

            int distancia = Math.abs(posUnidad.getX() - posObjetivo.getX()) + Math.abs(posUnidad.getY() - posObjetivo.getY());

            if (distancia <= tmp.getRangoAtaque()) { //Hemos establecido que la prioridad para la ia es que ataque si puede
                tmp.atacar(objetivo,tablero);

                if (objetivo.estaMuerta()) {
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
            }
        }
        turnoJugador = true;
        turnos ++;
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
        tablero.getCasilla(pos.getX(), pos.getY()).setUnidad(null);

        if (unidad.esEnergia()) {
            unidadesJugador.delete(unidad);
        } else {
            unidadesIA.delete(unidad);
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
        //Aqui tengo que trabajar
        for (int i=0; i< listaUnidades.getNumElementos();i++){
            Unidad unidad = listaUnidades.getElemento(i);
            Posicion pos = unidad.getPosicion();
            int x = pos.getX();
            int y = pos.getY();

            int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

            for (int[] dir : direcciones) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (tablero.posicionValida(newX, newY) && !tablero.getCasilla(newX, newY).isOcupada()) {
                    tablero.colocarUnidad(nuevaUnidad, newX, newY);
                    listaUnidades.add(nuevaUnidad);
                    return;
                }
            }
        }
    }

    public boolean juegoTerminado() {
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