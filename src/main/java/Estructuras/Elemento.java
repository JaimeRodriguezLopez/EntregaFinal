package Estructuras;

public class Elemento<T> {
    public T dato;
    public Elemento<T> siguiente;
    public Elemento(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
