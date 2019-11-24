package es.ucm.vdm.engine;

import es.ucm.vdm.engine.utils.Rect;

/**
 * Implementación del gestor gráfico del motor de tecnología
 * que permite el reescalado activo de los elementos que dibuja.
 * Clase basada en el patrón de diseño Strategy.
 */
public abstract class ScaledGraphics implements Graphics {

    protected int canvasLogicWidth_ = 1080;
    protected int canvasLogicHeight_ = 1920;

    private int canvasWidth_;
    private int canvasHeight_;
    private int canvasPosX_;
    private int canvasPosY_;

    /**
     * @return la altura del canvas lógico
     */
    public int getCanvasLogicHeight() {
        return canvasLogicHeight_;
    }

    /**
     * @return la altura del canvas lógico
     */
    public int getCanvasLogicWidth() {
        return  canvasLogicWidth_;
    }

    /**
     * Pinta un rectángulo del color indicado.
     * El rectángulo se reescalará en función del tamaño del canvas lógico.
     * @param rect  el rectángulo a pintar.
     * @param color el color con el que se debe pintar.
     */
    @Override
    public void fillRect(Rect rect, int color) {
        rect = scaleRect(rect);
        if (rect.y2 > canvasPosY_ + canvasHeight_) rect.y2 = canvasPosY_ + canvasHeight_;
        fillScaledRect(rect, color);
    }

    /**
     * Pinta una zona de una imagen en la posición y tamaño deseados.
     * El rectángulo de destino se reescalará en función del tamaño del canvas lógico.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src    el rectángulo fuente.
     * @param dst    el rectángulo destino.
     * @param alpha  la transparencia con la que se debe pintar.
     */
    @Override
    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha) {
        dst = scaleRect(dst);
        drawScaledPixmap(pixmap, src, dst, alpha);
    }

    /**
     * Método vacío que deben implementar las distintas plataformas del motor de tecnología.
     * @param pixmap el objeto tipo Pixmap con los datos de la imagen.
     * @param src    el rectángulo fuente.
     * @param dst    el rectángulo destino ya reescalado.
     * @param alpha  la transparencia con la que se debe pintar.
     */
    protected void drawScaledPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha) {}

    /**
     * Método vacío que deben implementar las distintas plataformas del motor de tecnología.
     * @param rect  el rectángulo a pintar ya reescalado.
     * @param color el color con el que se debe pintar.
     */
    protected void fillScaledRect(Rect rect, int color) {}

    /**
     * Cambia el tamaño lógico del canvas.
     * @param w nuevo ancho del canvas.
     * @param h nuevo alto del canvas.
     */
    public void setCanvasLogicSize(int w, int h) {
        canvasLogicWidth_ = w;
        canvasLogicHeight_ = h;
    }

    /**
     * Escala el tamaño del canvas real en función del tamaño de
     * la superficie de renderizado y del tamaño del canvas lógico.
     * Este reescalado mantiene el aspect ratio establecido por el
     * tamaño lógico del canvas.
     */
    public void scaleCanvas() {
        int wh = getHeight();
        int ww = getWidth();
        float logicAspectRatio = (float)canvasLogicHeight_ / (float)canvasLogicWidth_;
        float realAspectRatio = (float)wh / (float)ww;

        if (logicAspectRatio > realAspectRatio)
        {
            canvasHeight_ = wh;
            canvasWidth_ = (int)((float)canvasHeight_ / logicAspectRatio);
            canvasPosX_ = (ww - canvasWidth_) / 2;
            canvasPosY_ = 0;
        }
        else
        {
            canvasWidth_ = ww;
            canvasHeight_ = (int)((float)canvasWidth_ * logicAspectRatio);
            canvasPosY_ = (wh - canvasHeight_) / 2;
            canvasPosX_ = 0;
        }
    }

    /**
     * Escala un punto en el eje de coordenadas x.
     * @param x la coordenada a reescalar.
     * @return la coordenada reescalada.
     */
    private int scaleInAxisX(int x) {
        return canvasWidth_ * x / canvasLogicWidth_;
    }

    /**
     * Escala un punto en el eje de coordenadas y.
     * @param y la coordenada a reescalar.
     * @return la coordenada reescalada.
     */
    private int scaleInAxisY(int y) {
        return canvasHeight_ * y / canvasLogicHeight_;
    }

    /**
     * Escala un rectángulo utilizando los métodos auxiliares de reescalado de cada eje.
     * @param rect el rectángulo a reescalar.
     * @return el rectángulo reescalado.
     */
    public Rect scaleRect(Rect rect) {
        Rect r = new Rect(0, 0, 0,0);
        int newX1 = scaleInAxisX(rect.x1);
        int newY1 = scaleInAxisY(rect.y1);
        int newX2 = scaleInAxisX(rect.x2);
        int newY2 = scaleInAxisY(rect.y2);
        r.x1 = newX1 + canvasPosX_;
        r.x2 = newX2 + canvasPosX_;
        r.y1 = newY1 + canvasPosY_;
        r.y2 = newY2 + canvasPosY_;
        return r;
    }
}
