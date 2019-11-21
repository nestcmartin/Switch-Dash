package es.ucm.vdm.engine;

public interface Sound {

    public void play();
    public void stop();
    public void pause();

    void setLooping(boolean looping);
    void setVolume(float volume);

    public boolean isPlaying();
    public boolean isStopped();
    public boolean isLooping();

    public void dispose();
}