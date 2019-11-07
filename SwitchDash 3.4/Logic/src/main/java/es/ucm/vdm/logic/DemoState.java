package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.State;

public class DemoState extends State {

    public DemoState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {
        System.out.println("Demo state running!" + deltaTime);
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

    @Override
    public void render(double deltaTime) {
    }
}
