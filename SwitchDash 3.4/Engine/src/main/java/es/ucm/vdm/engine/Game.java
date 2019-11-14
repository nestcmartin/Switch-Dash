package es.ucm.vdm.engine;

public interface Game {

    public Input getInput();
    public ScaledGraphics getGraphics();

    public void setState(State state);
    public State getCurrentState();
    public State getStartState();

}
