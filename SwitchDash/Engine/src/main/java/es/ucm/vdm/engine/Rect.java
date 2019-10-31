package es.ucm.vdm.engine;

public class Rect {

    public Rect(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int x1;  // left
    public int y1;  // top
    public int x2;  // right
    public int y2;  // bottom

    public int width() {
        return x2 - x1;
    }

    public int height() {
        return y2 - y1;
    }

}
