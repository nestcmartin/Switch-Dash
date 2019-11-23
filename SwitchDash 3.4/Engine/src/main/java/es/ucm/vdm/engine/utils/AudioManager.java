package es.ucm.vdm.engine.utils;

import java.util.HashMap;
import java.util.Map;

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
    
    public void stopAllSounds() {
        for (Map.Entry mapElement : sounds_.entrySet()) {
            Sound s = (Sound) mapElement.getValue();
            s.stop();
        }
    }

    public void muteAllSounds() {
        for (Map.Entry mapElement : sounds_.entrySet()) {
            Sound s = (Sound) mapElement.getValue();
            s.setVolume(0.0f);
        }
    }

    public void unmuteAllSounds() {
        for (Map.Entry mapElement : sounds_.entrySet()) {
            Sound s = (Sound) mapElement.getValue();
            s.setVolume(1.0f);
        }
    }
}
