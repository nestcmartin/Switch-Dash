package es.ucm.vdm.engine.utilities;

public class Pair<X, Y> {

    private final X x_;
    private final Y y_;

    public X first() { return x_; }
    public Y second() { return y_; }

    public Pair(X x, Y y) {
        this.x_ = x;
        this.y_ = y;
    }

}
