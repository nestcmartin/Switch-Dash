package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

public class PulsatingSprite extends GameObject {

    private float alpha_ = 1f;
    private float pulsationSpeed_;
    private int adding_ = -1;

    public PulsatingSprite(Game g, Sprite s, int x, int y, int w, int h, float pulsationSpeed) {
        super(g, s, x, y, w, h);
        pulsationSpeed_ = pulsationSpeed;
    }

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

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        sprite_.draw(g, dstRect_, alpha_);
    }
}
