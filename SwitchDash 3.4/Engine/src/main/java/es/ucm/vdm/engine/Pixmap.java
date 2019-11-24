package es.ucm.vdm.engine;

/**
 * Interfaz del wrapper de objetos de tipo imagen.
 */
public interface Pixmap {

    /**
     * @return devuelve el ancho de la imagen.
     */
    public int getWidth();

    /**
     * @return devuelve el alto de la imagen.
     */
    public int getHeight();

    /**
     * Libera los recursos utilizados por el objeto Pixmap.
     */
    public void dispose();
}