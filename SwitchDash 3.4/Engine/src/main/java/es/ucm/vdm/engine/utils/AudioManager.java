package es.ucm.vdm.engine.utils;

import java.util.HashMap;

import es.ucm.vdm.engine.Sound;

public class AudioManager {

    HashMap<String, Sound> sounds_ = new HashMap<>();

    private static final AudioManager ourInstance = new AudioManager();
    public static AudioManager getInstance() {
        return ourInstance;
    }
    private AudioManager() {}

    public void addSound(String filename, Sound music) {
        sounds_.put(filename, music);
    }
    public Sound getSound(String filename) {
        return sounds_.get(filename);
    }
}
