package es.ucm.vdm.engine.utils;

/**
 * Representa un par de elementos de cualquier tipo.
 * @param <X>   el tipo  del primer elemento
 * @param <Y>   el tipo del segundo elemento
 */
public class Pair<X, Y> {

    private final X x_;
    private final Y y_;

    /**
     * Constructora de clase
     * @param x el primer elemento, de tipo X
     * @param y el segundo elemento, de tipo Y
     */
    public Pair(X x, Y y) {
        this.x_ = x;
        this.y_ = y;
    }

    /**
     * Devuelve el primer elemento del par como tipo objeto.
     * Es necesario hacer casting para obtener el tipo real.
     * @return el primer elemento.
     */
    public X first() { return x_; }

    /**
     * Devuelve el segundo elemento del par como tipo objeto.
     * Es necesario hacer casting para obtener el tipo real.
     * @return el segundo elemento.
     */
    public Y second() { return y_; }

}
