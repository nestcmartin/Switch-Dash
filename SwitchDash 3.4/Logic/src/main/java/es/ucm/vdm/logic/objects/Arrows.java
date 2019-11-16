package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Sprite;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.GameObject;

public class Arrows extends GameObject {

    private float pixelsPerSecond_ = 300;
    float floatY_ = 0f;
    int resetDistance_;

    public Arrows(Game g) {
        super(g);

        sprite_ = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]), 1, 1);
        w_ = sprite_.getImage().getWidth();
        h_ = sprite_.getImage().getHeight();
        x_ = (game_.getGraphics().getCanvasLogicWidth() - w_) / 2;

        resetDistance_ = -615;
        floatY_ = resetDistance_;
        y_ = resetDistance_;
    }

    public void incrementSpeed(int increment){
        pixelsPerSecond_ += increment;
    }

    @Override
    public void update(double deltaTime) {
        floatY_ += (pixelsPerSecond_ * deltaTime);
        if (floatY_ > 0) floatY_ += resetDistance_;
        y_ = (int)floatY_;
        updateDstRect();
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        sprite_.draw(g, dstRect_, 0.15f);
    }
}
