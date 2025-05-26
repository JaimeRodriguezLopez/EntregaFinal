package Estructuras;

public class Cola<T> implements Lista<T> {
    private final int Max;
    private int tamano = 0;
    private final T[] elementos;
    public Cola(int TamanoMax) {
        this.Max = TamanoMax;
        elementos = (T[]) new Object[this.Max];
    }
    public void queue(T elemento){
        if (tamano < this.Max) {
            elementos[tamano] = elemento;
            tamano++;
        }
    }
    public T dequeue(){
        if (tamano > 0) {
            T temporal = elementos[0];
            for (int i = 1; i < tamano; i++) {
                elementos[i-1] = elementos[i];
            }
            tamano--;
            elementos[tamano] = null;
            return temporal;
        }
        return null;
    }
    public boolean isEmpty(){
        return tamano == 0;
    }
    public boolean isFull(){
        return tamano == Max;
    }

    @Override
    public Iterador<T> getIterador() {
        return null;
    }

    @Override
    public boolean add(T elemento) {
        return true;
    }


    @Override
    public boolean delete(T elemento) {
        return true;
    }

    @Override
    public int getNumElementos() {
        return tamano;
    }
}
