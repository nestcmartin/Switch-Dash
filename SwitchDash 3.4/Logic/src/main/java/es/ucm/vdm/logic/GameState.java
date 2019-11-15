package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.State;

public class GameState extends State {

    protected List<GameObject> gameObjects_ = new ArrayList<>();

    public GameState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject go: gameObjects_) {
            go.update(deltaTime);
        }
    }

    @Override
    public void render(double deltaTime) {
        for (GameObject go: gameObjects_) {
            go.render(deltaTime);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
