package com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras;

class Nodo<T> {
    T dato;
    Nodo<T> anterior;
    Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
}
