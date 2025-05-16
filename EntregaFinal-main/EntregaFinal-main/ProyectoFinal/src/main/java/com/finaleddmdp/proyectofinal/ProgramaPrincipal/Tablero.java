package com.finaleddmdp.proyectofinal.ProgramaPrincipal;

import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.Cola;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.Diccionarios;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.ListaBasica;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.ListaSimplementeEnlazada;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Tablero {
    private NodoTablero[][] nodoTablero;
    private int base;
    private int altura;

    public Tablero(int base, int altura) {
        this.base = base;
        this.altura = altura;
        nodoTablero = new NodoTablero[base][altura];


        for (int x = 0; x < base; x++) {
            for (int y = 0; y < altura; y++) {
                nodoTablero[x][y] = new NodoTablero(x, y);
                nodoTablero[x][y].setTablero(this);
            }
        }
        for (int x = 0; x < base; x++) {
            for (int y = 0; y < altura; y++) {
                if (x > 0) nodoTablero[x][y].setOeste(nodoTablero[x - 1][y]);
                if (x < base - 1) nodoTablero[x][y].setEste(nodoTablero[x + 1][y]);
                if (y > 0) nodoTablero[x][y].setSur(nodoTablero[x][y - 1]);
                if (y < altura - 1) nodoTablero[x][y].setNorte(nodoTablero[x][y + 1]);
            }
        }

    }

    public int getBase() {
        return base;
    }

    public int getAltura() {
        return altura;
    }

    public NodoTablero getNodoTablero(int i, int j) {
        return nodoTablero[i][j];
    }

    public boolean moverNorte(@NotNull Unidad unidad){
        if (unidad.getCoordY() < getAltura() - 1){
            int x = unidad.getCoordX();
            int y = unidad.getCoordY();
            NodoTablero origen = getNodoTablero(x, y);
            NodoTablero destino = getNodoTablero(x, y + 1);

            if(!destino.getOcupado() && destino.isPenetrable() && unidad.movimientosRestantes > 0) {
                int costoMovimiento;
                try {
                    costoMovimiento = destino.getCostoMovimiento();

                } catch (NullPointerException e) {
                    costoMovimiento = 1;
                }
                if(unidad.getMovimientosRestantes() >=costoMovimiento) {
                    Unidad unidadAMover = origen.getUnidadEnCasilla();
                    origen.eliminarUnidadEnCasilla();
                    destino.setUnidadEnCasilla(unidadAMover);
                    unidad.setMovimientosRestantes(unidad.getMovimientosRestantes() - costoMovimiento);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moverSur(@NotNull Unidad unidad) {
        if (unidad.getCoordY() > 0) {
            int x = unidad.getCoordX();
            int y = unidad.getCoordY();
            NodoTablero origen = getNodoTablero(x, y);
            NodoTablero destino = getNodoTablero(x, y - 1);

            if(!destino.getOcupado() && destino.isPenetrable() && unidad.movimientosRestantes > 0) {
                int costoMovimiento;
                try {
                    costoMovimiento = destino.getCostoMovimiento();

                } catch (NullPointerException e) {
                    costoMovimiento = 1;
                }
                if(unidad.getMovimientosRestantes() >=costoMovimiento) {
                    Unidad unidadAMover = origen.getUnidadEnCasilla();
                    origen.eliminarUnidadEnCasilla();
                    destino.setUnidadEnCasilla(unidadAMover);
                    unidad.setMovimientosRestantes(unidad.getMovimientosRestantes() - costoMovimiento);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moverEste(@NotNull Unidad unidad) {
        if (unidad.getCoordX() < getBase() - 1){
            int x = unidad.getCoordX();
            int y = unidad.getCoordY();
            NodoTablero origen = getNodoTablero(x, y);
            NodoTablero destino = getNodoTablero(x + 1, y);
            if(!destino.getOcupado() && destino.isPenetrable() && unidad.movimientosRestantes > 0) {
                int costoMovimiento;
                try {
                    costoMovimiento = destino.getCostoMovimiento();

                } catch (NullPointerException e) {
                    costoMovimiento = 1;
                }
                if(unidad.getMovimientosRestantes() >=costoMovimiento) {
                    Unidad unidadAMover = origen.getUnidadEnCasilla();
                    origen.eliminarUnidadEnCasilla();
                    destino.setUnidadEnCasilla(unidadAMover);
                    unidad.setMovimientosRestantes(unidad.getMovimientosRestantes() - costoMovimiento);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moverOeste(@NotNull Unidad unidad) {
        if (unidad.getCoordX() > 0){
            int x = unidad.getCoordX();
            int y = unidad.getCoordY();
            NodoTablero origen = getNodoTablero(x, y);
            NodoTablero destino = getNodoTablero(x - 1, y);
            if(!destino.getOcupado() && destino.isPenetrable() && unidad.movimientosRestantes > 0) {
                int costoMovimiento;
                try {
                    costoMovimiento = destino.getCostoMovimiento();

                } catch (NullPointerException e) {
                    costoMovimiento = 1;
                }
                if(unidad.getMovimientosRestantes() >=costoMovimiento) {
                    Unidad unidadAMover = origen.getUnidadEnCasilla();
                    origen.eliminarUnidadEnCasilla();
                    destino.setUnidadEnCasilla(unidadAMover);
                    unidad.setMovimientosRestantes(unidad.getMovimientosRestantes() - costoMovimiento);
                    return true;
                }
            }
        }
        return false;
    }

    public void moverUnidadA(@NotNull Unidad unidad, @NotNull NodoTablero destino) {//Cuidado, este metodo ignora costos de movimiento
        NodoTablero origen= getNodoTablero(unidad.getCoordX(), unidad.getCoordY());
        Unidad unidadAMover = origen.getUnidadEnCasilla();
        origen.eliminarUnidadEnCasilla();
        destino.setUnidadEnCasilla(unidadAMover);
    }
    public int getDistancia(@NotNull Unidad inicio, @NotNull Unidad fin) {
        int CXI /*CoordenadaXInicio*/ = inicio.getCoordX();
        int CYI  = inicio.getCoordY();
        int CXF  = fin.getCoordX();
        int CYF = fin.getCoordY();
        return Math.abs((CXI - CXF))+Math.abs((CYI - CYF));
    }
    public void unidadAtaca(Unidad atacante, Unidad objetivo) {
        int distancia = getDistancia(atacante, objetivo);
        if (distancia <= atacante.RgAtaque) {
            atacante.atacar(objetivo);
        }
    }

    public Unidad buscarEnemigoMasCercano(Unidad unidad) {
        int xOrigen = unidad.getCoordX();
        int yOrigen = unidad.getCoordY();
        int numNodos = this.base*this.altura;
        NodoTablero origen = getNodoTablero(xOrigen, yOrigen);
        Cola<NodoTablero> porVisitar = new Cola<NodoTablero>(numNodos);
        ListaSimplementeEnlazada<NodoTablero> visitados = new ListaSimplementeEnlazada<NodoTablero>();
        int i;
        ListaBasica<NodoTablero> origenVecinos = origen.getVecinosPenetrables();
        for(i=0 ; i < origen.getVecinosPenetrables().getNumElementos();i++){
            porVisitar.queue(origenVecinos.getElemento(i));
        }
        visitados.add(origen);
        while(!porVisitar.isEmpty()){
            NodoTablero actual = porVisitar.dequeue();
            visitados.add(actual);
            if(actual.getUnidadEnCasilla() != null){
                if(actual.getUnidadEnCasilla().isJugable() != unidad.isJugable()){
                    return actual.getUnidadEnCasilla();
                }
            }else {
                visitados.add(actual);
                ListaBasica<NodoTablero> actualVecinos = actual.getVecinosPenetrables();
                for(i=0 ; i < actualVecinos.getNumElementos();i++){
                    NodoTablero vecino = actualVecinos.getElemento(i);
                    if(!visitados.contiene(vecino) && vecino.isPenetrable()){
                        porVisitar.queue(vecino);
                    }
                }
            }
        }
        return null;//imposible si hay unidades vivas en el tablero
    }
    public ListaBasica<NodoTablero> getCaminoMasCortoPorBFS(NodoTablero origen, NodoTablero destino) {
        Diccionarios<NodoTablero, NodoTablero> padre = new Diccionarios<>(this.base * this.altura);
        ListaSimplementeEnlazada<NodoTablero> visitados = new ListaSimplementeEnlazada<>();
        Cola<NodoTablero> porVisitar = new Cola<>(this.base * this.altura);

        porVisitar.queue(origen);
        visitados.add(origen);

        boolean encontrado = false;

        while (!porVisitar.isEmpty()) {
            NodoTablero actual = porVisitar.dequeue();

            if (actual == destino) {
                encontrado = true;
                break;
            }

            ListaBasica<NodoTablero> vecinos = actual.getVecinosPenetrables();
            for (int i = 0; i < vecinos.getNumElementos(); i++) {
                NodoTablero vecino = vecinos.getElemento(i);
                if (!visitados.contiene(vecino) && vecino.isPenetrable()) {
                    porVisitar.queue(vecino);
                    visitados.add(vecino);
                    padre.agregar(vecino, actual);
                }
            }
        }


        if (!encontrado) return null;

        // Reconstruir el camino desde el destino al origen usando el diccionario "padre"
        ListaBasica<NodoTablero> camino = new ListaBasica<>(this.base*this.altura);
        NodoTablero actual = destino;
        while (actual != null && actual != origen) {
            camino.insertarAlInicio(actual);
            actual = padre.obtener(actual);
        }
        camino.insertarAlInicio(origen); // Añadir el origen al principio

        return camino;
    }


    public Unidad buscarUnidadMasCercanaPorCosto(Unidad unidad) {
        NodoTablero origen = getNodoTablero(unidad.getCoordX(), unidad.getCoordY());
        int tamanio = this.base * this.altura;
        Diccionarios<NodoTablero, Integer> costoAcumulado = new Diccionarios<>(tamanio);
        Diccionarios<NodoTablero, NodoTablero> padre = new Diccionarios<>(tamanio);
        ListaSimplementeEnlazada<NodoTablero> visitados = new ListaSimplementeEnlazada<>();

        Cola<NodoTablero> porVisitar = new Cola<>(tamanio);
        porVisitar.queue(origen);
        costoAcumulado.agregar(origen, 0);

        while (!porVisitar.isEmpty()) {
            NodoTablero actual = porVisitar.dequeue();
            int costoActual = costoAcumulado.obtener(actual);

            if (actual.getUnidadEnCasilla() != null && actual.getUnidadEnCasilla().isJugable() != unidad.isJugable()) {
                return actual.getUnidadEnCasilla();
            }

            ListaBasica<NodoTablero> vecinos = actual.getVecinosPenetrables();
            for (int i = 0; i < vecinos.getNumElementos(); i++) {
                NodoTablero vecino = vecinos.getElemento(i);
                if (!visitados.contiene(vecino) && vecino.isPenetrable()) {
                    int costoMovimiento = vecino.getCostoMovimiento();
                    int nuevoCosto = costoActual + costoMovimiento;

                    if (!costoAcumulado.contieneClave(vecino) || nuevoCosto < costoAcumulado.obtener(vecino)) {
                        costoAcumulado.reemplazarValor(vecino, nuevoCosto);
                        padre.reemplazarValor(vecino, actual);
                        porVisitar.queue(vecino);
                    }
                }
            }
            visitados.add(actual);
        }

        return null;
    }

    public static boolean guardarTablero(Tablero tablero, String ruta) {
        Gson gson = new Gson();
        try(FileWriter fw = new FileWriter(ruta)) {
            gson.toJson(tablero, fw);
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean guardarTablero(Tablero tablero) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("tablero.json")) {
            gson.toJson(tablero, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Tablero leerTablero(String ruta) {
        Gson gson = new Gson();
        try(FileReader fr = new FileReader(ruta)) {
            return gson.fromJson(fr, Tablero.class);
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public Tablero leerTablero() {
        Gson gson = new Gson();
        try(FileReader fr = new FileReader("Tablero.json")){
            return gson.fromJson(fr, Tablero.class);
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}