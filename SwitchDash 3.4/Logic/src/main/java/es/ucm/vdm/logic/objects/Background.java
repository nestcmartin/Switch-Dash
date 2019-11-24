package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Random;
import es.ucm.vdm.engine.utils.Rect;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que se encarga de renderizar el fondo del juego.
 * Pinta dos colores planos, primero pinta toda la pantalla de un color,
 * y luego pinta una franja vertical con el color que le corresponde al primer color.
 */
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

    /**
     * Constructora de clase.
     * @param game referencia al juego de Game que gestiona el bucle.
     */
    public Background(Game game) {
        super(game);

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
