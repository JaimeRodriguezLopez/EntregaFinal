package ProgramaPrincipal.Estructuras;

public class Diccionarios<K, V> {
    private Object[] claves;
    private Object[] valores;
    private int tamanio;

    public Diccionarios() {
        this.claves = new Object[10]; // Tamaño inicial
        this.valores = new Object[10];
        this.tamanio = 0;
    }

    public void agregar(K clave, V valor) {
        for (int i = 0; i < tamanio; i++) {
            if (claves[i].equals(clave)) {
                valores[i] = valor; // Si la clave ya existe, actualiza el valor
                return;
            }
        }
        claves[tamanio] = clave;
        valores[tamanio] = valor;
        tamanio++;
    }

    public V obtener(K clave) {
        for (int i = 0; i < tamanio; i++) {
            if (claves[i].equals(clave)) {
                return (V) valores[i];
            }
        }
        return null;
    }

    public void eliminar(K clave) {
        for (int i = 0; i < tamanio; i++) {
            if (claves[i].equals(clave)) {
                for (int j = i; j < tamanio - 1; j++) {
                    claves[j] = claves[j + 1];
                    valores[j] = valores[j + 1];
                }
                claves[tamanio - 1] = null;
                valores[tamanio - 1] = null;
                tamanio--;
                return;
            }
        }
    }

    public boolean contieneClave(K clave) {
        for (int i = 0; i < tamanio; i++) {
            if (claves[i].equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public boolean contieneValor(V valor) {
        for (int i = 0; i < tamanio; i++) {
            if (valores[i].equals(valor)) {
                return true;
            }
        }
        return false;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return tamanio == 0;
    }

    public void limpiar() {
        for (int i = 0; i < tamanio; i++) {
            claves[i] = null;
            valores[i] = null;
        }
        tamanio = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < tamanio; i++) {
            sb.append(claves[i]).append("=").append(valores[i]);
            if (i < tamanio - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }



}
