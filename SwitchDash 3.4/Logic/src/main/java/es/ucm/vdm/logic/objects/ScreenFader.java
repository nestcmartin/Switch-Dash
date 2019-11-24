package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que sirve para las transiciones entre escenas.
 * Hace transiciones del sprite entre opaco y transparente.
 */
public class ScreenFader extends GameObject {

    private float fadeTime_;
    private float currentFadeTime_;
    private float alpha_;
    boolean fadeIn_;

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     * @param sprite sprite del GameObject
     * @param fadeTime duración del fade (en segundos)
     */
    public ScreenFader(Game game, Sprite sprite, float fadeTime) {
        super(game, sprite, 0, 0, game.getGraphics().getCanvasLogicWidth(), game.getGraphics().getCanvasLogicHeight());
        fadeTime_ = fadeTime;
        currentFadeTime_ = 0;
        alpha_ = 0;
        fadeIn_ = true;
    }

    /**
     * @return true si está realizando un fade, false en caso contrario
     */
    public boolean isFading(){ return currentFadeTime_ > 0; }

    public void startFadeIn(boolean fadeIn){
        currentFadeTime_ = fadeTime_;
        fadeIn_ = fadeIn;
    }

    /**
     * Actualiza el timer y el alpha del fade
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        if(currentFadeTime_ >= 0){
            currentFadeTime_ -= deltaTime;
            alpha_ = currentFadeTime_/ fadeTime_;
            if(!fadeIn_)
                alpha_ = Math.min(fadeTime_ - alpha_, 1);
        }
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();

        if(alpha_ > 0)
            sprite_.draw(g, dstRect_, alpha_);
    }
}
