package es.ucm.vdm.engine.desktop;

import java.awt.Image;
import es.ucm.vdm.engine.Pixmap;

/**
 * Implementación para Desktop de la interfaz Pixmap.
 */
public class DesktopPixmap implements Pixmap {

    private Image image_;

    /**
     * Constructora de clase.
     * @param image la imagen de java.
     */
    public DesktopPixmap(Image image) {
        this.image_ = image;
    }

    /**
     * @return la imagen de java gestionada por este wrapper.
     */
    public Image getImage() {
        return image_;
    }

    /**
     * @return el ancho de la imagen.
     */
    @Override
    public int getWidth() {
        return image_.getWidth(null);
    }

    /**
     * @return el alto de la imagen.
     */
    @Override
    public int getHeight() {
        return image_.getHeight(null);
    }

    /**
     * Libera el recurso de imagen de java.
     */
    @Override
    public void dispose() {
        image_.flush();
    }
}
