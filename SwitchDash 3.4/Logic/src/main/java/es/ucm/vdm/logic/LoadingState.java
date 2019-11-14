package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utilities.PixmapManager;

public class LoadingState extends State {

    public LoadingState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {
        ScaledGraphics g = game_.getGraphics();

        for (int i = 0; i < Assets.images.length; i++) {
            PixmapManager.getInstance().addPixmap(Assets.images[i], g.newPixmap(Assets.images[i]));
        }

        game_.setState(new MainState(game_));
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
