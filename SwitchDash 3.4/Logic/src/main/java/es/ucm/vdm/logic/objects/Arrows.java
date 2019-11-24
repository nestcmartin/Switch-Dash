package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que renderiza las flechas del fondo del juego.
 */
public class Arrows extends GameObject {

    private float pixelsPerSecond_ = 300;
    float floatY_;
    int resetDistance_;

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     */
    public Arrows(Game game) {
        super(game);

        sprite_ = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]), 1, 1);
        w_ = sprite_.getImage().getWidth();
        h_ = sprite_.getImage().getHeight();
        x_ = (game_.getGraphics().getCanvasLogicWidth() - w_) / 2;

        resetDistance_ = -615;
        floatY_ = resetDistance_;
        y_ = resetDistance_;
    }


    /**
     * Incrementa la velocidad a la que se mueven las flechas verticalmente
     * @param increment cantidad que se suma a la velocidad actual de las flechas
     */
    public void incrementSpeed(int increment){
        pixelsPerSecond_ += increment;
    }


    /**
     * Actualiza las flechas moviéndolas verticalmente.
     * Si han pasado del punto en el que se repiten, las reseteamos y
     * las movemos a la posición inicial.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
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
