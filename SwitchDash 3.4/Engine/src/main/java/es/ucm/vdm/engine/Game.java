package es.ucm.vdm.engine;

public interface Game {

    public Input getInput();
    //public FileIO getFileIO();
    public Graphics getGraphics();
    //public Audio getAudio();

    public void setScreen(State screen);
    public State getCurrentScreen();
    public State getStartScreen();

}
