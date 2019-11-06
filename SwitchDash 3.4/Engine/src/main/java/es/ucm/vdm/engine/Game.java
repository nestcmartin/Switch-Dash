package es.ucm.vdm.engine;

public interface Game {

    public Input getInput();
    //public FileIO getFileIO();
    public Graphics getGraphics();
    //public Audio getAudio();

    public void setState(State state);
    public State getCurrentState();
    public State getStartState();

}
