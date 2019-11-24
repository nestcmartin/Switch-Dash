package es.ucm.vdm.engine;

import es.ucm.vdm.engine.utils.Rect;

/**
 * Interfaz del gestor gráfico del motor de tecnología.
 */
public interface Graphics {

    /**
     * Devuelve un objeto de tipo Pixmap.
     * @param fileName el nombre y extensión del archivo de imagen fuente.
     * @return un nuevo objeto de tipo Pixmap.
     */
    public Pixmap newPixmap(String fileName);

    /**
     * Pinta toda la superficie de renderizado de un color.
     * @param color el color con el que se debe pintar.
     */
    public void clear(int color);

    /**
     * Pinta un rectángulo de color.
     * @param rect el rectángulo a pintar.
     * @param color el color con el que se debe pintar.
     */
    public void fillRect(Rect rect, int color);

    /**
     * Pinta una imagen en la posición indicada.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param x la coordenada x en la que se debe pintar la imagen.
     * @param y la coordenada y en la que se debe pintar la imagen.
     */
    public void drawPixmap(Pixmap pixmap, int x, int y);

    /**
     * Pinta una zona de una imagen en la posición indicada.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src el rectángulo fuente.
     * @param x la coordenada x en la que se debe pintar la zona de la imagen.
     * @param y la coordenada y en la que se debe pintar la zona de la imagen.
     */
    public void drawPixmap(Pixmap pixmap, Rect src, int x, int y);

    /**
     * Pinta una zona de una imagen en la posición y tamaño indicados.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src el rectángulo fuente.
     * @param dst el rectángulo destino.
     * @param alpha la transparencia con la que se debe pintar.
     */
    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha);

    /**
     * @return el ancho de la superficie de renderizado.
     */
    public int getWidth();

    /**
     * @return el alto de la superficie de renderizado.
     */
    public int getHeight();

}
