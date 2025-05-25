package Estructuras;

public interface Iterador<T>{
    boolean hasNext();
    T next();
    void delete();
    Object insert(T elemento);
}
