package ProgramaPrincipal.Unidades;
import ProgramaPrincipal.Unidad;
public class ManInTheMiddle extends Unidad {
    public ManInTheMiddle(){//se hace pasar por uno de los del otro bando (Una vez cada x rondas no se le puede atacar)
        super("Man In The Middle",200,5,10,6,3,false);
    }
}
