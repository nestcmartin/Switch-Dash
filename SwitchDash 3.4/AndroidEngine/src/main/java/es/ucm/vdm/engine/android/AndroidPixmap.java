package es.ucm.vdm.engine.android;

import android.graphics.Bitmap;

import es.ucm.vdm.engine.Pixmap;

/**
 * Implementación para Android de la interfaz Pixmap.
 */
public class AndroidPixmap implements Pixmap {

    private Bitmap bitmap_;

    /**
     * Constructora de clase.
     * @param bitmap la imagen de Android.
     */
    public AndroidPixmap(Bitmap bitmap) {
        this.bitmap_ = bitmap;
    }

    /**
     * @return la imagen de Android gestionada por este wrapper.
     */
    public Bitmap getBitmap() {
        return bitmap_;
    }

    /**
     * @return el ancho de la imagen.
     */
    @Override
    public int getWidth() {
        return bitmap_.getWidth();
    }

    /**
     * @return el alto de la imagen.
     */
    @Override
    public int getHeight() {
        return bitmap_.getHeight();
    }

    /**
     * Libera el recurso de imagen de Android.
     */
    @Override
    public void dispose() {
        bitmap_.recycle();
    }
}