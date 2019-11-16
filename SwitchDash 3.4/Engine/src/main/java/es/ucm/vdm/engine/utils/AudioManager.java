package es.ucm.vdm.engine.utils;

import java.util.HashMap;

import es.ucm.vdm.engine.Music;
import es.ucm.vdm.engine.Sound;

public class AudioManager {

    HashMap<String, Sound> sounds_ = new HashMap<>();
    HashMap<String, Music> musics_ = new HashMap<>();

    private static final AudioManager ourInstance = new AudioManager();
    public static AudioManager getInstance() {
        return ourInstance;
    }
    private AudioManager() {}

    public void addSound(String filename, Sound sound) {
        sounds_.put(filename, sound);
    }
    public Sound getSound(String filename) {
        return sounds_.get(filename);
    }
    public void addMusic(String filename, Music music) {
        musics_.put(filename, music);
    }
    public Music getMusic(String filename) {
        return musics_.get(filename);
    }
}
