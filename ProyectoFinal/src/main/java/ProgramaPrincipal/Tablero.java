package ProgramaPrincipal;

import org.jetbrains.annotations.NotNull;

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
                    costoMovimiento = destino.getModUsoMovimiento();

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
                    costoMovimiento = destino.getModUsoMovimiento();

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
                    costoMovimiento = destino.getModUsoMovimiento();

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
                    costoMovimiento = destino.getModUsoMovimiento();

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
        } else {
            System.out.println(atacante.getNombre() + " está fuera de rango para atacar a " + objetivo.getNombre());
        }
    }

}