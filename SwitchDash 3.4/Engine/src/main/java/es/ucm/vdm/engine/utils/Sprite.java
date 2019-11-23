package es.ucm.vdm.engine.utils;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

/**
 * Define una imagen y un rectángulo fuente.
 */
public class Sprite {

    private Rect srcRect_;
    private Pixmap image_;

    private int frameRow_ = 0;
    private int frameCol_ = 0;
    private int frameWidth_;
    private int frameHeight_;

    /**
     * Constructora de clase.
     * @param image la imagen del sprite-sheet.
     * @param numRows el número de filas del sprite-sheet.
     * @param numCols el número de columnas del sprite-sheet.
     */
    public Sprite(Pixmap image, int numRows, int numCols) {
        image_ = image;

        frameWidth_ = image_.getWidth() / numCols;
        frameHeight_ = image_.getHeight() / numRows;

        updateSourceRect();
    }

    /**
     * @return la imagen del sprite-sheet.
     */
    public Pixmap getImage() { return image_; }

    /**
     * @return el ancho de un frame del sprite-sheet.
     */
    public int getWidth() { return frameWidth_; }

    /**
     * @return la altura de un frame del sprite-sheet.
     */
    public int getHeight() { return frameHeight_; }

    /**
     * @return la fila actual del sprite.
     */
    public int getFrameRow() { return frameRow_; }

    /**
     * @return la columna actual del sprite.
     */
    public int getFrameCol() { return frameCol_; }

    /**
     * Cambia la fila actual y actualiza el rectángulo fuente del sprite.
     * @param row la fila nueva del sprite.
     */
    public void setFrameRow(int row) { frameRow_ = row; updateSourceRect(); }

    /**
     * Cambia la columna actual y actualiza el rectángulo fuente del sprite.
     * @param col la columna nueva del sprite.
     */
    public void setFrameCol(int col) { frameCol_ = col; updateSourceRect(); }

    /**
     * Dibuja el sprite en el rectángulo destino deseado.
     * @param g el objeto Graphics que se encarga de dibujar la imagen adecuadamente.
     * @param dst el rectángulo destino en el que se desea pintar el sprite.
     */
    public void draw(Graphics g, Rect dst) {
        g.drawPixmap(image_, srcRect_, dst, 1);
    }

    /**
     * Dibuja el sprite en el rectángulo destino deseado con el valor de alpha indicado.
     * @param g el objeto Graphics que se encarga de dibujar la imagen adecuadamente.
     * @param dst el rectángulo destino en el que se desea pintar el sprite.
     * @param alpha el valor de transparencia con el que se pintará la imagen [0.0 - 1.0].
     */
    public void draw(Graphics g, Rect dst, float alpha) {
        g.drawPixmap(image_, srcRect_, dst, alpha);
    }

    /**
     * Actualiza la posición y tamaño del rectángulo fuente del sprite.
     */
    private void updateSourceRect() {
        srcRect_ = new Rect(frameCol_ * frameWidth_,
                            frameRow_ * frameHeight_,
                           (frameCol_ * frameWidth_) + frameWidth_,
                           (frameRow_ * frameHeight_) + frameHeight_);
    }
}