public class UnidadData {
    private String nombre;
    private int hp;
    private int ataque;
    private int defensa;
    private int rangoMovimiento;
    private int rangoAtaque;
    private int maximoUsoHabilidad;
    private int posicionX;
    private int posicionY;
    private boolean esEnergia;

    public UnidadData() {}

    public UnidadData(Unidad unidad) {
        this.nombre = unidad.getNombre();
        this.hp = unidad.getHp();
        this.ataque = unidad.getAtaque();
        this.defensa = unidad.getDefensa();
        this.rangoMovimiento = unidad.getRangoMovimiento();
        this.rangoAtaque = unidad.getRangoAtaque();
        this.maximoUsoHabilidad = unidad.getMaximoUsoHabilidad();
        this.esEnergia = unidad.esEnergia();

        if (unidad.getPosicion() != null) {
            this.posicionX = unidad.getPosicion().getX();
            this.posicionY = unidad.getPosicion().getY();
        }
    }
    //Lo vamos a usar para, una vez tenemos los datos, crear la unidad a los que corresponde
    public Unidad crearUnidad() {
        Unidad unidad = null;
        if(nombre.equals("CentralNuclear")) {
            unidad = new CentralNuclear();
        }
        else if(nombre.equals("CentralEolica")) {
            unidad = new CentralEolica();
        }
        else if(nombre.equals("CentralHidroelectrica")) {
            unidad = new CentralHidroelectrica();
        }
        else if(nombre.equals("Malware")) {
            unidad = new Malware();
        }
        else if(nombre.equals("Ransomware")) {
            unidad = new Ransomware();
        }
        else if(nombre.equals("Phishing")) {
            unidad = new Phishing();
        }
        if (unidad != null) {
            unidad.setHp(this.hp);
            unidad.setAtaque(this.ataque);
            unidad.setDefensa(this.defensa);
            unidad.setRangoMovimiento(this.rangoMovimiento);
            unidad.setRangoAtaque(this.rangoAtaque);
            unidad.setMaximoUsoHabilidad(this.maximoUsoHabilidad);
            unidad.setPosicion(new Posicion(this.posicionX, this.posicionY));
        }

        return unidad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getRangoMovimiento() {
        return rangoMovimiento;
    }
    public void setRangoMovimiento(int rangoMovimiento) {
        this.rangoMovimiento = rangoMovimiento;
    }

    public int getRangoAtaque() {
        return rangoAtaque;
    }
    public void setRangoAtaque(int rangoAtaque) {
        this.rangoAtaque = rangoAtaque;
    }

    public int getMaximoUsoHabilidad() {
        return maximoUsoHabilidad;
    }
    public void setMaximoUsoHabilidad(int maximoUsoHabilidad) {
        this.maximoUsoHabilidad = maximoUsoHabilidad;
    }

    public int getPosicionX() {
        return posicionX;
    }
    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }
    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public boolean isEsEnergia() {
        return esEnergia; }
    public void setEsEnergia(boolean esEnergia) {
        this.esEnergia = esEnergia; }
}
