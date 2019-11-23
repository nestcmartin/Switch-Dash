package es.ucm.vdm.engine.utils;

/**
 * Defineun rectángulo con dos puntos (esquina superior
 * izquierda y esquina inferior derecha).
 */
public class Rect {

    public int x1;  // left
    public int y1;  // top
    public int x2;  // right
    public int y2;  // bottom

    /**
     * Constructora de clase sin parámetros.
     */
    public Rect() {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
    }

    /**
     * Constructora de clase con parámetros
     * @param x1 coordenada x de la esquina superior izquierda
     * @param y1 coordenada y de la esquina superior izquierda
     * @param x2 coordenada x de la esquina inferior derecha
     * @param y2 coordenada y de la esquina inferior derecha
     */
    public Rect(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Calcula el ancho del rectángulo.
     * @return el ancho del rectángulo.
     */
    public int width() {
        return x2 - x1;
    }

    /**
     * Calcula el alto del rectángulo.
     * @return la altura del rectángulo.
     */
    public int height() {
        return y2 - y1;
    }

}