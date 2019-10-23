package es.ucm.vdm.engine;

public interface Audio {

    Music newMusic(String filename);
    Sound newSound(String filename);

}