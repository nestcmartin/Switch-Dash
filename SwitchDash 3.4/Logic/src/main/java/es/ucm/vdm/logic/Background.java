package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Random;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class Background extends GameObject {

    private Rect auxDstRect_ = new Rect();
    private int currentBackgroundColor_ = 0;

    private int[] backgroundColors_ = {
            0x41a85f,
            0x00a885,
            0x3d8eb9,
            0x2969b0,
            0x553982,
            0x28324e,
            0xf37934,
            0xd14b41,
            0x75706b
    };

    public Background(Game g) {
        super(g);

        sprite_ = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.BACKGROUNDS.ordinal()]), 1, 9);
        w_ = (1080 - sprite_.getImage().getWidth()) / 2;
        h_ = (2220 - sprite_.getImage().getHeight()) / 2;

        auxDstRect_.x1 = x_;
        auxDstRect_.y1 = y_;
        auxDstRect_.x2 = 1080;
        auxDstRect_.y2 = 2220;
    }


    public void updateColor() {
        currentBackgroundColor_ = Random.randomInt(0, backgroundColors_.length - 1);
    }

    @Override
    public void render(double deltaTime) {
        super.render(deltaTime);
        Graphics g = game_.getGraphics();
        g.fillRect(auxDstRect_, backgroundColors_[currentBackgroundColor_]);
    }
}
