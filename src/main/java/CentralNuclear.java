public class CentralNuclear extends UnidadEnergia{
    public CentralNuclear() {
        super("Central Nuclear", 150, 40, 30, 2, 3,2);
    }

    @Override
    public void habilidad(Unidad objetivo, Tablero tablero) {
        if(this.getMaximoUsoHabilidad() <= 0){
            return;
        }
        this.setAtaque(this.getAtaque() + 20); //La habilidad especial le da 20 de daño mas en el ataque pero autorecibe 5 de daño por la "contaminacion"
        atacar(objetivo,tablero);
        this.setAtaque(this.getAtaque()-20);
        this.setHp(this.getHp() - 5);
        this.setMaximoUsoHabilidad(this.getMaximoUsoHabilidad() -1);
    }
}
