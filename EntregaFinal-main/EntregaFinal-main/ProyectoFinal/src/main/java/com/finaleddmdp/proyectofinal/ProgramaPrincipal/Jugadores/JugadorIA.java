package com.finaleddmdp.proyectofinal.ProgramaPrincipal.Jugadores;

import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.Iterador;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras.ListaBasica;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Jugador;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.NodoTablero;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Tablero;
import com.finaleddmdp.proyectofinal.ProgramaPrincipal.Unidad;

public class JugadorIA extends Jugador {

    public JugadorIA() {
        super("Virus");
    }

    @Override
    public void terminarTurno(Tablero tablero) {
        Iterador<Unidad> iterador = unidades.getIterador();
        while (iterador.hasNext()) {
            Unidad unidad = iterador.next();
            actuarConUnidad(unidad);
        }
    }
    public void actuarConUnidad(Unidad unidad) {//Localiza el mas cercano, calcula la ruta, se mueve, intenta atacar, fin de turno
        Unidad objetivo;
        Tablero tablero = unidad.getTablero();
        objetivo = tablero.buscarUnidadMasCercanaPorCosto(unidad);
        ListaBasica<NodoTablero> camino= tablero.getCaminoMasCortoPorBFS(tablero.getNodoTablero(unidad.getCoordX(),unidad.getCoordY()),tablero.getNodoTablero(objetivo.getCoordX(),objetivo.getCoordY()));
        boolean finbucle = false;
        while (!finbucle) {
            for(int i = 0; i< camino.getNumElementos();i++){
                int costo = 0;
                for(int j = 0; j< (camino.getNumElementos()-i-1) ;j++){//El -1 es porque el metodo que da camino t lleva hasta la casilla donde esta el enemigo pero a ese no podemos entrar asi que no elimino de la posibilidad de entrar
                    costo += camino.getElemento(j).getCostoMovimiento();
                }
                if(costo<unidad.getMovimientosRestantes()){
                    NodoTablero destino = camino.getElemento(camino.getNumElementos()-i-1);
                    tablero.moverUnidadA(unidad, destino);
                    unidad.setMovimientosRestantes(unidad.getMovimientosRestantes()-costo);
                    finbucle = true;
                }

            }
            finbucle = true;
        }
        tablero.unidadAtaca(unidad,objetivo);

    }
}
