package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Rect;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

/**
 * GameObject que sirve como botón en el juego
 */
public class Button extends GameObject {

    private Rect scaledRect_;

    /**
     * Constructora de clase.
     * @param game referencia al juego de Game que gestiona el bucle.
     * @param sprite sprite del GameObject
     * @param x coordenada x del GameObject en pantalla
     * @param y coordenada y del GameObject en pantalla
     * @param w ancho del GameObject con el que se debe renderizar
     * @param h alto del GameObject con el que se debe renderizar
     */
    public Button(Game game, Sprite sprite, int x, int y, int w, int h) {
        super(game, sprite, x, y, w, h);
    }

    @Override
    protected void updateDstRect() {
        super.updateDstRect();
        scaledRect_ = game_.getGraphics().scaleRect(dstRect_);
    }

    /**
     * @param event Evento de input táctil / de ratón
     * @return true si el tipo del evento es el de soltar y las coordenadas x,y del evento
     *         están dentro del rect del botón, false en caso contrario
     */
    public boolean handleTouchEvent(Input.TouchEvent event){
        return (event.type_ == Input.EventType.RELEASED && isInsideButton(event.x_, event.y_));
    }

    /**
     * Verifica si las coordenadas x,y están dentro del botón
     * @param x coordenada x en la pantalla
     * @param y coordenada y en la pantalla
     * @return true si las coordenadas x,y están dentro del rect del botón,
     *         false en caso contrario
     */
    private boolean isInsideButton(int x, int y){
        updateDstRect();
        return x > scaledRect_.x1 &&
               x < scaledRect_.x2 &&
               y > scaledRect_.y1 &&
               y < scaledRect_.y2;
    }
}
