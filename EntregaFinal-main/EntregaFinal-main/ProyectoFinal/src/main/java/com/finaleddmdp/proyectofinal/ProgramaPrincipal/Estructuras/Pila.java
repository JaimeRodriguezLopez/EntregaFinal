package com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras;

public class Pila<T> implements Lista<T>{
    private final int Max;
    private int tamano = 0;
    private final T[] elementos;
    public Pila(int TamanoMax){
        this.Max = TamanoMax;
        elementos = (T[]) new Object[this.Max];
    }
    public void push(T elemento){
        if (tamano < this.Max) {
            elementos[tamano] = elemento;
            incTamano();
        }
    }
    private void incTamano(){
        tamano++;
    }
    private void decTamano(){
        tamano--;
    }
    public T pop(){
        if (tamano > 0) {
            T temporal = this.elementos[tamano-1];
            decTamano();
            return temporal;
        }
        return null;
    }
    public T getCima(){
        if (tamano>0) {
            return elementos[tamano - 1];
        }
        else{
            return null;
        }
    }
    public boolean isEmpty(){
        return tamano == 0;
    }
    public boolean isFull(){
        return tamano == this.Max;
    }
    @Override
    public int getNumElementos() {
        return tamano;
    }
    @Override
    public boolean add(T elemento) {
        if (tamano < this.Max) {
            push(elemento);
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(T elemento) {
        if (tamano > 0) {
            pop();
            return true;
        }
        return false;
    }
    @Override
    public Iterador<T> getIterador() {
        return null;
    }


}
