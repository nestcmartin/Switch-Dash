package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utilities.PixmapManager;

public class LoadingState extends State {

    Game game_;

    public LoadingState(Game game) {
        super(game);
        this.game_ = game;
    }

    @Override
    public void update(double deltaTime) {
        Graphics g = game_.getGraphics();
        Pixmap p = g.newPixmap("arrowsBackground.png");
        PixmapManager.getInstance().addPixmap("arrowsBackground.png", p);
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xffff0000);
        game_.setState(new DemoState(game_));
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
