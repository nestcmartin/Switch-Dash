package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Random;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class Background extends GameObject {

    private Rect canvasBackGroundRect_ = new Rect();
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

        w_ = PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]).getWidth();
        h_ = PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]).getHeight();
        x_ = (1080 - w_) / 2;
        y_ = (2220 - h_) / 2;

        canvasBackGroundRect_.x1 = 0;
        canvasBackGroundRect_.y1 = 0;
        canvasBackGroundRect_.x2 = 1080;
        canvasBackGroundRect_.y2 = 2220;
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.fillRect(canvasBackGroundRect_, backgroundColors_[currentBackgroundColor_]);
        sprite_.draw(g, dstRect_);
    }

    public void updateColor() {
        currentBackgroundColor_ = Random.randomInt(0, backgroundColors_.length - 1);
        sprite_.setFrameCol(currentBackgroundColor_);
        updateDstRect();
    }
}
