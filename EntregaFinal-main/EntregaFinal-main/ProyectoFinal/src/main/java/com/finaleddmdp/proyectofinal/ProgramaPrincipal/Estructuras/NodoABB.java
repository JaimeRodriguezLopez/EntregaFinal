package com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras;

class NodoABB<K, V> {
    K clave;
    V valor;
    NodoABB<K, V> menor;  // Hijo izquierdo
    NodoABB<K, V> mayor;  // Hijo derecho

    public NodoABB(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.menor = null;
        this.mayor = null;
    }

    public K getClave() { return clave; }
    public V getValor() { return valor; }
    public NodoABB<K, V> getMenor() { return menor; }
    public NodoABB<K, V> getMayor() { return mayor; }

    public void setMenor(NodoABB<K, V> menor) { this.menor = menor; }
    public void setMayor(NodoABB<K, V> mayor) { this.mayor = mayor; }
}