package es.ucm.vdm.engine;

public abstract class Screen {

    protected final Game game_;

    public Screen(Game game) {
        this.game_ = game;
    }

    public abstract void update(float deltaTime);
    public abstract void render(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();

}
