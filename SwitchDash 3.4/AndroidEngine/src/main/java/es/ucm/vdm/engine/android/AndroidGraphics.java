package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utils.Rect;

/**
 * Implementación para Android de la interfaz Graphics.
 */
public class AndroidGraphics extends ScaledGraphics {

    private AssetManager assets_;
    private Canvas canvas_;
    private Paint paint_;

    android.graphics.Rect srcRect_;
    android.graphics.Rect dstRect_;

    /**
     * Constructora de clase.
     * @param assets gestor de Assests de Android.
     * @param frameBuffer superficie de renderizado.
     */
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets_ = assets;
        this.canvas_ = new Canvas(frameBuffer);
        this.paint_ = new Paint();

        // Mejora el rendimiento global de la aplicación, ya que
        // evitamos hacer new en cada llamada a los métodos.
        srcRect_ = new android.graphics.Rect();
        dstRect_ = new android.graphics.Rect();
    }

    /**
     * Devuelve un nuevo objeto de tipo AndroidPixmap.
     * @param fileName el nombre y extensión del archivo de imagen fuente.
     * @return el objeto de tipo AndroidPixmap.
     */
    @Override
    public Pixmap newPixmap(String fileName) {
        InputStream in = null;
        Bitmap bitmap;
        try {
            String[] imgPath = assets_.list(".");
            System.out.println(imgPath);
            in = assets_.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) {
                throw new RuntimeException("No se pudo cargar la imagen '" + fileName + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la imagen '" + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new AndroidPixmap(bitmap);
    }

    /**
     * Pinta toda la superficie de renderizado de un color.
     * @param color el color con el que se debe pintar.
     */
    @Override
    public void clear(int color) {
        canvas_.drawRGB((color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                (color & 0xff));
    }

    /**
     * Implementación real del método de ScaledGraphics que
     * pinta un rectángulo reescalado del color especificado.
     * @param rect  el rectángulo a pintar ya reescalado.
     * @param color el color con el que se debe pintar.
     */
    @Override
    protected void fillScaledRect(Rect rect, int color) {
        dstRect_.set(rect.x1, rect.y1, rect.x2, rect.y2);
        paint_.setStyle(Paint.Style.FILL);
        paint_.setColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & color))));
        canvas_.drawRect(dstRect_, paint_);
    }

    /**
     * Implementación real del método de ScaledGraphics que
     * pinta una zona de una imagen en una posición y con un tamaño ya reescalados.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src    el rectángulo fuente.
     * @param dst    el rectángulo destino ya reescalado.
     * @param alpha  la transparencia con la que se debe pintar.
     */
    @Override
    protected void drawScaledPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha) {
        srcRect_.set(src.x1, src.y1, src.x2, src.y2);
        dstRect_.set(dst.x1, dst.y1, dst.x2, dst.y2);
        paint_.setAlpha((int) (alpha * 255));
        canvas_.drawBitmap(((AndroidPixmap) pixmap).getBitmap(), srcRect_, dstRect_, paint_);
    }

    /**
     * Pinta una imagen en la posición indicada, a tamaño real sin escalar.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param x      la coordenada x en la que se debe pintar la imagen.
     * @param y      la coordenada y en la que se debe pintar la imagen.
     */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), x, y, null);
    }

    /**
     * Pinta una zona de una imagen en la posición indicada, a tamaño real sin escalar.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src    el rectángulo fuente.
     * @param x      la coordenada x en la que se debe pintar la zona de la imagen.
     * @param y      la coordenada y en la que se debe pintar la zona de la imagen.
     */
    @Override
    public void drawPixmap(Pixmap pixmap, Rect src, int x, int y) {
        srcRect_.set(src.x1, src.y1, src.x2, src.y2);
        dstRect_.set(x, y, x + src.width(), y + src.height());
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), srcRect_, dstRect_, null);
    }

    /**
     * @return el ancho de la superficie de renderizado.
     */
    @Override
    public int getWidth() {
        return canvas_.getWidth();
    }

    /**
     * @return el alto de la superficie de renderizado.
     */
    @Override
    public int getHeight() {
        return canvas_.getHeight();
    }
}