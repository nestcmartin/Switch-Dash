package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class Arrows extends GameObject {

    private float scrollSpeed_ = 20;
    Rect auxDstRect_ = new Rect();

    public Arrows(Game g) {
        super(g);

        sprite_ = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]), 1, 1);
        w_ = sprite_.getImage().getWidth();
        h_ = sprite_.getImage().getHeight();
        x_ = (1080 - w_) / 2;
        y_ = (2220 - h_) / 2;
    }

    @Override
    public void update(double deltaTime) {
        System.out.println(deltaTime);

        y_ += (scrollSpeed_ * deltaTime);
        if (y_ > h_) y_ -= h_;

        super.update(deltaTime);

        auxDstRect_.x1 = x_;
        auxDstRect_.y1 = y_ - h_;
        auxDstRect_.x2 = x_ + w_;
        auxDstRect_.y2 = y_;
    }

    @Override
    public void render(double deltaTime) {
        super.render(deltaTime);

        Graphics g = game_.getGraphics();
        sprite_.draw(g, auxDstRect_);
    }
}
