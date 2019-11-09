package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Scaler;

public class LoadingState extends State {

    private Game game_;
    private static final int logicWidth_ = 1080;
    private static final int logicHeight_ = 2220;

    public LoadingState(Game game) {
        super(game);
        this.game_ = game;
    }

    @Override
    public void update(double deltaTime) {
        Scaler.setCanvasLogicSize(logicWidth_, logicHeight_);

        Graphics g = game_.getGraphics();

        for (int i = 0; i < Assets.imageFiles.length; i++) {
            PixmapManager.getInstance().addPixmap(Assets.imageFiles[i], g.newPixmap(Assets.imageFiles[i]));
        }

        game_.setState(new DemoState(game_));
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
