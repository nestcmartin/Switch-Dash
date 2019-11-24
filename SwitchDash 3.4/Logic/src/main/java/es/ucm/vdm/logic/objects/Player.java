package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que tiene la funcionalidad del jugador
 */
public class Player extends GameObject {

    private boolean isAlive_ = true;
    private boolean isWhite_ = true;
    private float sizeMult_ = 1f;
    private float width_;
    private float height_;
    private int gameWidth_;
    private int gameHeight_;

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     * @param sprite sprite del GameObject
     * @param x coordenada x del GameObject en pantalla
     * @param y coordenada y del GameObject en pantalla
     * @param w ancho del GameObject con el que se debe renderizar
     * @param h alto del GameObject con el que se debe renderizar
     * @param gameWidth ancho de la ventana de juego
     * @param gameHeight alto de la ventana de juego
     */
    public Player(Game game, Sprite sprite, int x, int y, int w, int h, int gameWidth, int gameHeight) {
        super(game, sprite, x, y, w, h);
        width_ = w;
        height_ = h;
        gameWidth_ = gameWidth;
        gameHeight_ = gameHeight;
    }

    /**
     * @return el color actual del jugador
     */
    public int getColor(){ return isWhite_? 0 : 1; }

    /**
     * Se llama cuando el jugador se equivoca de color y pierde.
     * Hace que el jugador deje de renderizarse.
     */
    public void die(){ isAlive_ = false; }

    /**
     * @param event evento de input de teclado
     * @return true si el jugador ha cambiado de color, false en caso contrario
     */
    public boolean handleKeyEvent(Input.KeyEvent event){
        if(event.type_ == Input.EventType.PRESSED) {
            if (event.keyChar_ == ' ')
            {
                switchColor();
                return true;
            }
        }
        return false;
    }

    /**
     * @param event evento de input de ratón / táctil
     * @return true si el jugador ha cambiado de color, false en caso contrario
     */
    public boolean handleTouchEvent(Input.TouchEvent event){
        if(event.type_ == Input.EventType.PRESSED) {
            switchColor();
            return true;
        }
        return false;
    }

    /**
     * Hace que el jugador cambie de color al color contrario
     * También actualiza el sizeMultiplier para que haga una pequeña animación
     */
    private void switchColor(){
        isWhite_ = !isWhite_;
        int color = isWhite_ ? 0 : 1;
        sprite_.setFrameRow(color);
        sizeMult_ = 0.8f;
    }

    /**
     * Actualiza las coordenadas x,y del GameObject y el rect destino en el que se va a pintar
     * teniendo el cuenta el sizeMultipliyer actual
     */
    private void updateSize(){
        w_ = (int)(sizeMult_ * width_);
        h_ = (int)(sizeMult_ * height_);
        x_ = ((gameWidth_ - w_) / 2);
        updateDstRect();
    }


    /**
     * Actualiza el tamaño del player para que haga una pequeña
     * animación creciendo cuando cambie de color.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        if (sizeMult_ < 1f)
            sizeMult_ += deltaTime;
        else sizeMult_ = 1;
        updateSize();
    }


    /**
     * Renderiza el jugador si sigue vivo
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void render(double deltaTime) {
        if(isAlive_)
            super.render(deltaTime);
    }
}
