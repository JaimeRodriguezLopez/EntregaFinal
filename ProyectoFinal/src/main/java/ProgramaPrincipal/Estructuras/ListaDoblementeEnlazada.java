package ProgramaPrincipal.Estructuras;

public class ListaDoblementeEnlazada<T> implements Lista<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int numElementos;

    public ListaDoblementeEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.numElementos = 0;
    }

    @Override
    public boolean add(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
        numElementos++;
        return true;
    }

    @Override
    public boolean delete(T elemento) {
        if (cabeza == null) return false;

        Nodo<T> actual = cabeza;
        while (actual != null && !actual.dato.equals(elemento)) {
            actual = actual.siguiente;
        }

        if (actual == null) return false;

        if (actual.anterior != null) {
            actual.anterior.siguiente = actual.siguiente;
        } else {
            cabeza = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente.anterior = actual.anterior;
        } else {
            cola = actual.anterior;
        }

        numElementos--;
        return true;
    }

    @Override
    public Iterador<T> getIterador() {
        return new IteradorListaDoblementeEnlazada();
    }

    @Override
    public int getNumElementos() {
        return numElementos;
    }

    private class IteradorListaDoblementeEnlazada implements Iterador<T> {
        private Nodo<T> actual = cabeza;
        private Nodo<T> previo = null;

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            T dato = actual.dato;
            previo = actual;
            actual = actual.siguiente;
            return dato;
        }

        @Override
        public void delete() {
            if (previo == null) {
                throw new IllegalStateException("Debe llamar a next() antes de eliminar.");
            }
            ListaDoblementeEnlazada.this.delete(previo.dato);
            previo = null;
        }
        @Override
        public Object insert(T elemento) {
            Nodo<T> nuevo = new Nodo<>(elemento);
            if (previo == null) { // Insertar al inicio
                nuevo.siguiente = cabeza;
                if (cabeza != null) {
                    cabeza.anterior = nuevo;
                }
                cabeza = nuevo;
                if (cola == null) {
                    cola = nuevo;
                }
            } else { // Insertar después del previo
                nuevo.siguiente = previo.siguiente;
                nuevo.anterior = previo;
                if (previo.siguiente != null) {
                    previo.siguiente.anterior = nuevo;
                }
                previo.siguiente = nuevo;
                if (nuevo.siguiente == null) {
                    cola = nuevo;
                }
            }
            numElementos++;
            return null;
        }



    }
}