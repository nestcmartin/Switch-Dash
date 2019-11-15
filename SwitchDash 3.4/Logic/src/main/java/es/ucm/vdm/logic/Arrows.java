package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Sprite;

public class Arrows extends GameObject {

    private float scrollSpeed_ = 300;
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

    @Override
    public void update(double deltaTime) {
        floatY_ += (scrollSpeed_ * deltaTime);
        if (floatY_ > 0) floatY_ += resetDistance_;
        y_ = (int)floatY_;
        updateDstRect();
    }

    @Override
    public void render(double deltaTime) {
        super.render(deltaTime);

        Graphics g = game_.getGraphics();
        sprite_.draw(g, dstRect_);
    }
}
