package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

public class ScreenFader extends GameObject {

    private float fadeTime_;
    private float currentFadeTime_;
    private float alpha_;
    boolean fadeIn_;

    public ScreenFader(Game g, Sprite s, float fadeTime) {
        super(g, s, 0, 0, g.getGraphics().getCanvasLogicWidth(), g.getGraphics().getCanvasLogicHeight());
        fadeTime_ = fadeTime;
        currentFadeTime_ = 0;
        alpha_ = 0;
        fadeIn_ = true;
    }

    public boolean isFading(){ return currentFadeTime_ > 0; }

    public void startFadeIn(boolean fadeIn){
        currentFadeTime_ = fadeTime_;
        fadeIn_ = fadeIn;
    }

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
