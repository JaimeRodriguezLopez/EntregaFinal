package Estructuras;

public class ListaBasica<T> implements Lista<T> {
    private T[] array;
    private int pointer = 0;
    private int numElementos;

    public ListaBasica(int capacidad){
        array = (T[]) new Object[capacidad];
        numElementos = 0;
    }
    @Override
    public boolean add(T elemento){
        if(numElementos < array.length){
            array[numElementos] = elemento;
            numElementos = numElementos + 1;
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean delete(T elemento){
        if(numElementos == 0){
            return false;
        }
        else{
            for (int i = 0; i < numElementos; i++){
                if(array[i].equals(elemento)){
                    array[i] = null;
                    for(int j = i; j < numElementos; j++){
                        array[j] = array[j+1];
                    }
                    array[numElementos-1] = null;
                    numElementos = numElementos - 1;
                    if(pointer > 1){
                        pointer--;
                    }
                    return true;
                }
            }
            return false;
        }
    }
    public void insertarAlInicio(T elemento) {
        if(numElementos < array.length) {
            for (int i = numElementos; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = elemento;
            numElementos++;
        }
    }
    public boolean contieneElemento(T elemento){
        for (int i = 0; i < numElementos; i++){
            if(array[i].equals(elemento)){
                return true;
            }
        }
        return false;
    }
    public int getNumElementos(){
        return numElementos;
    }
    public void clear() {
        for (int i = 0; i < numElementos; i++) {
            array[i] = null;
        }
        numElementos = 0;
    }
    public T remove(int index) {
        if (index < 0 || index >= numElementos) {
            throw new IndexOutOfBoundsException();
        }
        T removed = array[index];
        for (int i = index; i < numElementos - 1; i++) {
            array[i] = array[i + 1];
        }
        array[numElementos - 1] = null;
        numElementos--;
        return removed;
    }
    public boolean isEmpty() {
        return (getNumElementos()==0);
    }
 public T getElemento(int i){
        return array[i];
 }
    @Override
    public Iterador<T> getIterador() {

        return new Iterador<T>() {
            private static Boolean puedeEliminar = false;
            @Override
            public boolean hasNext() {
                return numElementos > pointer;
            }

            @Override
            public T next() {
                if(!hasNext()){
                    return null;
                } else {
                    pointer = pointer + 1;
                    puedeEliminar = true;
                    return array[pointer-1];
                }
            }
            @Override
            public boolean delete() {
                if (!puedeEliminar) {
                    throw new IllegalStateException("No se puede eliminar sin llamar a next()");
                } else {
                    for(int i = ListaBasica.this.pointer - 1; i < ListaBasica.this.numElementos - 1; ++i) {
                        ListaBasica.this.array[i] = ListaBasica.this.array[i + 1];
                    }

                    ListaBasica.this.array[ListaBasica.this.numElementos - 1] = null;
                    --ListaBasica.this.numElementos;
                    --ListaBasica.this.pointer;
                }
                return false;
            }


            @Override
            public Object insert(T elemento) {
                if(numElementos < array.length){
                    for (int i = array.length; i > pointer; i--){
                        array[i] = array[i-1];
                    }
                    array[pointer] = elemento;

                }
                numElementos = numElementos + 1;
                return elemento;
            }
        };
    }

}
