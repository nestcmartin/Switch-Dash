package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que se renderiza alternando entre transparente y
 * opaco de forma contínua según un intervalo.
 * Se usa en el juego para los sprites 'Tap to continue'
 */
public class PulsatingSprite extends GameObject {

    private float alpha_ = 1f;
    private float pulsationSpeed_;
    private int adding_ = -1;

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     * @param sprite sprite del GameObject
     * @param x coordenada x del GameObject en pantalla
     * @param y coordenada y del GameObject en pantalla
     * @param w ancho del GameObject con el que se debe renderizar
     * @param h alto del GameObject con el que se debe renderizar
     * @param pulsationSpeed veelocidad a la que cambia de transparencia
     */
    public PulsatingSprite(Game game, Sprite sprite, int x, int y, int w, int h, float pulsationSpeed) {
        super(game, sprite, x, y, w, h);
        pulsationSpeed_ = pulsationSpeed;
    }


    /**
     * Acutaliza el alpha del GameObject
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        alpha_ += pulsationSpeed_ * adding_ * (float)deltaTime;

        if(alpha_ > 1){
            alpha_ = 1;
            adding_ *= -1;
        }
        else if(alpha_ < 0){
            alpha_ = 0;
            adding_ *= -1;
        }
    }


    /**
     * Renderiza el sprite del GameObject con el alpha correspondiente.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        sprite_.draw(g, dstRect_, alpha_);
    }
}
