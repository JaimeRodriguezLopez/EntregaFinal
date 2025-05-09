package ProgramaPrincipal.Unidades;
import ProgramaPrincipal.Unidad;
public class Electrica extends Unidad {
    public Electrica(){//Cuando ataca a una unidad, esa ya no se puede mover
        super("Electrica", 400, 30, 10, 5, 3,true);
    }

}
