package ProgramaPrincipal;

public abstract class Unidad {
    private int maximoUsoHabilidad;
    private String nombre;
    private int hp;
    private int hpReal;
    private int ataque;
    private int defensa;
    private int defensaReal;
    private int rangoMovimiento;
    private int MovimientosRestantes;
    private int rangoAtaque;
    private Posicion posicion;
    private boolean ataquedisponible;
    private boolean haAtacadoEsteTurno = false;
    private boolean haMovidoEsteTurno = false;

    public Unidad(String nombre, int hp, int ataque, int defensa, int rangoMovimiento, int rangoAtaque, int maximoUsoHabilidad) {
        this.nombre = nombre;
        this.hp = hp;
        this.hpReal = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.defensaReal = defensa;
        this.rangoMovimiento = rangoMovimiento;
        this.MovimientosRestantes = rangoMovimiento;
        this.rangoAtaque = rangoAtaque;
        this.maximoUsoHabilidad = maximoUsoHabilidad;
        this.ataquedisponible = true;

    }

    public int getMaximoUsoHabilidad() {
        return maximoUsoHabilidad;
    }

    public void setMaximoUsoHabilidad(int maximoUsoHabilidad) {
        this.maximoUsoHabilidad = maximoUsoHabilidad;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setRangoMovimiento(int rangoMovimiento) {
        this.rangoMovimiento = rangoMovimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRangoAtaque(int rangoAtaque) {
        this.rangoAtaque = rangoAtaque;
    }

    public String getNombre() {
        return nombre;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getRangoMovimiento() {
        return rangoMovimiento;
    }

    public int getRangoAtaque() {
        return rangoAtaque;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getDefensaReal() {
        return defensaReal;
    }

    public void setDefensaReal(int defensaReal) {
        this.defensaReal = defensaReal;
    }

    public int getMovimientosRestantes() {
        return MovimientosRestantes;
    }

    public void setMovimientosRestantes(int rangoMovimientoReal) {
        this.MovimientosRestantes = rangoMovimientoReal;
    }

    public int getHpReal() {
        return hpReal;
    }

    public void setHpReal(int hpReal) {
        this.hpReal = hpReal;
    }

    public boolean Ataquedisponible() {
        return ataquedisponible;
    }

    public void setAtaquedisponible(boolean ataquedisponible) {
        this.ataquedisponible = ataquedisponible;
    }

    public boolean HaAtacadoEsteTurno() {
        return haAtacadoEsteTurno;
    }

    public void setHaAtacadoEsteTurno(boolean haAtacadoEsteTurno) {
        this.haAtacadoEsteTurno = haAtacadoEsteTurno;
    }

    public boolean HaMovidoEsteTurno() {
        return haMovidoEsteTurno;
    }

    public void setHaMovidoEsteTurno(boolean haMovidoEsteTurno) {
        this.haMovidoEsteTurno = haMovidoEsteTurno;
    }

    public int atacar(Unidad objetivo, Tablero tablero) {
        double factor = Math.random()*2; //Pues asi lo pediste en el enunciado
        int dano =  (int)((this.ataque*factor) - objetivo.getDefensa());
        if (dano < 0) dano = 0;
        Posicion pos = objetivo.getPosicion();
        Casilla casilla = tablero.getCasilla(pos.getX(), pos.getY());
        int defensaTotal = objetivo.getDefensa() + casilla.getModificadorDefensa();
        objetivo.setHp(objetivo.getHp() - dano);
        return dano;
    }
    public boolean estaMuerta() {
        return hp <= 0;
    }
    @Override
    public String toString() {
        return ("Nombre: "+ nombre + " , Vida: " + hp);
    }
    public abstract boolean esEnergia();//Las clases Unidadenergia y unidadIa van a extender de esta, y van a overridear este metodo para indicar la faccion a la que pertenecen

}

