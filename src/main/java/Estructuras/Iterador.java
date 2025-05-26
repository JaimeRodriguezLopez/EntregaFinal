package Estructuras;

public interface Iterador<T>{
    boolean hasNext();
    T next();
    boolean delete();
    Object insert(T elemento);
}
