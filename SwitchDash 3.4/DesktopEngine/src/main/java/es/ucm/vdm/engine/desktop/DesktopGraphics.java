package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utils.Rect;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * Implementación para Desktop de la interfaz Graphics (ScaledGraphics).
 */
public class DesktopGraphics extends ScaledGraphics {

    private java.awt.Graphics graphics_;
    private BufferStrategy strategy_;
    private JFrame window_;

    /**
     * Constructora de clase.
     * Crea un BufferStrategy.
     * @param window la ventana de JFrame.
     */
    public DesktopGraphics(JFrame window) {
        this.window_ = window;

        int i = 100;
        while(i-- > 0) {
            try {
                window_.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
                System.err.println("Error creando BufferStrategy; reintentando...");
            }
        }
        if (i == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        strategy_ = window_.getBufferStrategy();
    }


    /**
     * Crea y devuelve un nuevo objeto de tipo DesktopPixmap.
     * @param fileName el nombre y extensión del archivo de imagen fuente.
     * @return el objeto de tipo DesktopPixmap.
     */
    public Pixmap newPixmap(String fileName) {
        Image image = null;
        try{
            image = javax.imageio.ImageIO.read(new java.io.File(DesktopGame.assetsPath_ + fileName));
        }
        catch (IOException e){
            System.err.println("The image could not be loaded");
        }
        return new DesktopPixmap(image);
    }

    /**
     * Convierte un color en formato entero (hexadecimal) a un objeto Color de java.
     * @param color el color en formato entero.
     * @return un objeto Color de java.
     */
    private Color intToColor(int color){
        int r = (color>>16)&0xFF;
        int g = (color>>8)&0xFF;
        int b = (color>>0)&0xFF;
        return new Color(r, g, b);
    }

    /**
     * Pinta toda la superficie de renderizado de un color.
     * @param color el color con el que se debe pintar.
     */
    public void clear(int color) {
        graphics_.setColor(intToColor(color));
        graphics_.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Implementación real del método de ScaledGraphics que
     * pinta un rectángulo reescalado del color especificado.
     * @param rect  el rectángulo a pintar ya reescalado.
     * @param color el color con el que se debe pintar.
     */
    @Override
    protected void fillScaledRect(Rect rect, int color) {
        graphics_.setColor(intToColor(color));
        graphics_.fillRect(rect.x1, rect.y1, rect.width(), rect.height());
    }

    /**
     * Pinta una imagen en la posición indicada, a tamaño real sin escalar.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param x      la coordenada x en la que se debe pintar la imagen.
     * @param y      la coordenada y en la que se debe pintar la imagen.
     */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(), x, y, null);
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
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                x, y, x + src.width(), y + src.height(),
                src.x1, src.y1, src.x2, src.y2,
                null);
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
        if(alpha == 1) {
            drawPixmapBasic(pixmap, src, dst);
        }
        else {
            Graphics2D graphics2D = (Graphics2D)graphics_;
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            drawPixmapBasic(pixmap, src, dst);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }

    /**
     * Envoltorio para el método drawImage de java graphics.
     * Pinta una zona de una imagen en una posición y con un tamaño ya reescalados.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src    el rectángulo fuente.
     * @param dst    el rectángulo destino ya reescalado.
     */
    private void drawPixmapBasic(Pixmap pixmap, Rect src, Rect dst){
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                dst.x1, dst.y1, dst.x2, dst.y2,
                src.x1, src.y1, src.x2, src.y2,
                null);
    }

    /**
     * @return el ancho de la superficie de renderizado.
     */
    public int getWidth() {
        return window_.getWidth();
    }

    /**
     * @return el alto de la superficie de renderizado.
     */
    public int getHeight() {
        return window_.getHeight();
    }

    /**
     * Le pide graphics a BufferStrategy y lo guarda
     */
    public void setGraphics(){
        while(true) {
            try {
                graphics_ = strategy_.getDrawGraphics();
                break;
            } catch (Exception e) {
            }
        }
    }

    /**
     * Libera graphics
     */
    public void dispose() {
        graphics_.dispose();
    }

    /**
     * @return el BufferStrategy
     */
    public BufferStrategy getBufferStrategy() { return strategy_; }
}
