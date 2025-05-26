package ProgramaPrincipal;

import Estructuras.ListaBasica;
import Excepciones.CasillaOcupadaException;
import Excepciones.MovimientoFueraDelTableroException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class  Tablero {
    private static final Logger logger = LogManager.getLogger(Tablero.class);
    private int filas;
    private int columnas;
    private Casilla[][] casillas;
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new Casilla[filas][columnas];
        //Ojo, queremos que 1 de cada 3 casillas sean "montañas", en las cuales cuesta más llegar pero se esta mas protegido (da mas defensa).
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int rand = (int)(Math.random() * 3); //Como random() devuelve un double del 0 al 1, lo multiplicamos x3 y cogemos la parte entera, por tanto devuelve 0,1 o 2 de forma aleatoria; solo si es 2 se va a crear esa montaña:
                if (rand != 2){
                    casillas[i][j] = new Casilla(1, 0);
                }
                else{
                    casillas[i][j] = new Casilla(2, 20);
                }
            }
        }
    }
    public Tablero(int filas, int columnas, Casilla[][] casillas) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.casillas[i][j] = casillas[i][j];
            }
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Casilla getCasilla(int x, int y) {
        if (x >= 0 && x < filas && y >= 0 && y < columnas) {
            return casillas[x][y];
        }
        return null;
    }

    public boolean posicionValida(int x, int y) {
        return x >= 0 && x < filas && y >= 0 && y < columnas;
    }

    public boolean colocarUnidad(Unidad unidad, int x, int y) throws MovimientoFueraDelTableroException, CasillaOcupadaException {
        try {
            if (!posicionValida(x, y)) { //Vemos si la casilla esta dentro del tablero
                throw new MovimientoFueraDelTableroException(x, y, filas, columnas);
            }

            if (casillas[x][y].isOcupada()) {
                String ocupante = casillas[x][y].getUnidad().getNombre();
                throw new CasillaOcupadaException(x, y, ocupante);
            }
            casillas[x][y].setUnidad(unidad);
            unidad.setPosicion(new Posicion(x, y));
            logger.info("Unidad " + unidad.getNombre() + " colocada en (" + x + "," + y + ")");
            return true;

        } catch (MovimientoFueraDelTableroException | CasillaOcupadaException e) {
            logger.warn("Error al colocar unidad: " + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Error inesperado al colocar unidad: " + e.getMessage());
            return false;
        }
    }
    public boolean moverUnidad(Unidad unidad, int xDestino, int yDestino) {
        Posicion posActual = unidad.getPosicion();
        if (posActual == null) {
            return false; //Si no esta en el tablero, obviamente no deberiamos de poder moverla
        }
        int xActual = posActual.getX();
        int yActual = posActual.getY();
        if (!posicionValida(xDestino, yDestino) || casillas[xDestino][yDestino].isOcupada()) {
            return false; //Aqui, verificamos que el destino este dentro del tablero y que no este ocupado
        }
        int costoTotal = calcularCostoMovimiento(xActual, yActual, xDestino, yDestino);
        //Abajo implementamos el metodo calcularcostomovimiento, usando dijkstra
        if (costoTotal == -1 || costoTotal > unidad.getRangoMovimiento()) {
            return false; //Si no le quedan movimientos para afrontar el costo o si no hay camino posible ( por ejemplo, movernos a una casilla rodeada de oponentes, no hay caminos posibles
        }
        casillas[xActual][yActual].setUnidad(null);
        casillas[xDestino][yDestino].setUnidad(unidad);
        unidad.setPosicion(new Posicion(xDestino, yDestino));
        //Aqui ya hemos movido nuestra ficha
        return true;
    }
    private int calcularCostoMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino) {
        // Si origen y destino son iguales, devuelve 0 obviamente
        if (xOrigen == xDestino && yOrigen == yDestino) {
            return 0;
        }
        // Inicializamos las estructuras que vamos a usar, una para las distancias y otra para guardar si cada casilla ha sido o no visitada
        int[][] distancia = new int[filas][columnas];
        boolean[][] visitado = new boolean[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                distancia[i][j] = filas*columnas*10; // Inicializamos con un valor muy alto, teoricamente la distancia no puede ser mayor que filas*columnas*10 ni de lejos
            }
        }
        // La distancia al origen es 0
        distancia[xOrigen][yOrigen] = 0;
        // Direcciones: arriba, abajo, izquierda y dcha
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        //Usamos dijstra:
        int minX = 0, minY = 0;
        for (int count = 0; count < filas * columnas && minX != -1; count++) {
            // Encontrar el nodo no visitado con menor distancia
            int minDistancia = filas*columnas*10;
            minX = -1;
            minY = -1;

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    if (!visitado[i][j] && distancia[i][j] < minDistancia) {
                        minDistancia = distancia[i][j];
                        minX = i;
                        minY = j;
                    }
                }
            }

            //Si la casilla es valida, ajustamos para que se sepa que la hemos visitado
            if (minX != -1) {
                visitado[minX][minY] = true;
                if (minX == xDestino && minY == yDestino) {
                    return distancia[minX][minY]; //Si es el destino, hemos terminado
                }

                // Actualizar distancias de los vecinos
                for (int[] dir : direcciones) {
                    int vecinoX = minX + dir[0];
                    int vecinoY = minY + dir[1];
                    //Vemos si los vecinos pueden estar en el tablero
                    //Estamos usando continue, que no lo hemos visto en clase, pero basicamente lo que hace es pasar a la siguiente iteracion del for;
                    //Por ejemplo, en la condicion de abajo, si el vecino no esta en las dimensiones del tablero, se continua al siguiente paso del for para coger una nueva direccion.
                    if (!posicionValida(vecinoX, vecinoY)) {
                        continue;
                    }
                    // No se puede atravesar casillas ocupadas
                    if (casillas[vecinoX][vecinoY].isOcupada()) {
                        continue;
                    }

                    // Si ya ha sido visitado, de nuevo, continuamos con el bucle
                    if (visitado[vecinoX][vecinoY]) {
                        continue;
                    }
                    int nuevaDistancia = distancia[minX][minY] + casillas[vecinoX][vecinoY].getCostoMovimiento();
                    // Si encontramos un camino más corto, actualizamos
                    if (nuevaDistancia < distancia[vecinoX][vecinoY]) {
                        distancia[vecinoX][vecinoY] = nuevaDistancia;
                    }
                }
            }
        }
        //Para distinguier cuando no se ha encontrado un camino, devolvemos -1.
        return -1;
    }

    public ListaBasica<Unidad> obtenerTodasLasUnidades() {
        ListaBasica<Unidad> unidades = new ListaBasica<>(filas*columnas);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].isOcupada()) {
                    unidades.add(casillas[i][j].getUnidad());
                }
            }
        }

        return unidades;
    }
    public Casilla[][] getCasillas() {
        return casillas;
    }
}
