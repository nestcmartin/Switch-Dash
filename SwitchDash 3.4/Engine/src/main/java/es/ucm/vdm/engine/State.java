package es.ucm.vdm.engine;

public abstract class State {

    protected final Game game_;

    public State(Game game) {
        this.game_ = game;
    }

    public abstract void update(double deltaTime);
    public abstract void render(double deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();

}
