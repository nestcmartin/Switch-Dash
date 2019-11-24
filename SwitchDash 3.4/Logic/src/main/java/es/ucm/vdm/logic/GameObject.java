package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Rect;
import es.ucm.vdm.engine.utils.Sprite;


/**
 * Un GameObject es un contenedor para un sprite, update y render.
 * También tiene posición (x,y) además de ancho y alto con el que se debe renderizas.
 * Sirve para generalizar y facilitar la gestión de objetos en los estados.
 */
public class GameObject {

    protected Game game_;

    protected int x_;
    protected int y_;
    protected int w_;
    protected int h_;

    protected Sprite sprite_;
    protected Rect dstRect_ = new Rect();

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     * @param sprite sprite del GameObject
     * @param x coordenada x del GameObject en pantalla
     * @param y coordenada y del GameObject en pantalla
     * @param w ancho del GameObject con el que se debe renderizar
     * @param h alto del GameObject con el que se debe renderizar
     */
    public GameObject(Game game, Sprite sprite, int x, int y, int w, int h) {
        game_ = game;
        sprite_ = sprite;
        x_ = x;
        y_ = y;
        w_ = w;
        h_ = h;
        updateDstRect();
    }

    /**
     * Constructora de clase alternativa
     * @param game referencia al juego de Game que gestiona el bucle
     */
    public GameObject(Game game) {
        game_ = game;
        x_ = 0;
        y_ = 0;
        w_ = 0;
        h_ = 0;
    }

    // Getters para la posición (x,y), ancho y alto del GameObject
    public int getX() { return x_; }
    public int getY() { return y_; }
    public int getW() { return w_; }
    public int getH() { return h_; }

    // Setters para la posición (x,y), ancho y alto del GameObject
    public void setX(int x) { x_ = x; }
    public void setY(int y) { y_ = y; }
    public void setW(int w) { w_ = w; }
    public void setH(int h) { h_ = h; }

    /**
     * Cambia la posición x,y del GameObject
     * @param x nueva posición x del GameObject
     * @param y nueva posición y del GameObject
     */
    public void setPosition(int x, int y) { setX(x); setY(y); }

    /**
     * Método a implementar por las clases que hereden de esta.
     * Contiene el comportamiento / funcionalidad del GameObject.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    public void update(double deltaTime) {}

    /**
     * Actualiza el rect destino en el que se va a renderizar
     * el GameObject según su información actual.
     */
    protected void updateDstRect(){
        dstRect_.x1 = x_;
        dstRect_.y1 = y_;
        dstRect_.x2 = x_ + w_;
        dstRect_.y2 = y_ + h_;
    }

    /**
     * Renderiza el sprite del GameObject en el rect destino.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        sprite_.draw(g, dstRect_);
    }

    /**
     * Cambia la fila y columna actual del sprite.
     * @param row la fila nueva del sprite.
     * @param col la columna nueva del sprite.
     */
    public void updateSpriteFrame(int row, int col) {
        sprite_.setFrameCol(col);
        sprite_.setFrameRow(row);
    }
}
