package es.ucm.vdm.engine.utils;

import java.util.HashMap;
import java.util.Map;

import es.ucm.vdm.engine.Sound;

/**
 * Gestor de los recursos de Audio (Música y Sonidos).
 * Clase basada en el patrón de diseño Singleton.
 */
public class AudioManager {

    private HashMap<String, Sound> sounds_ = new HashMap<>();
    private static final AudioManager ourInstance = new AudioManager();

    /**
     * Constructora de clase.
     */
    private AudioManager() {

    }

    /**
     * Permite acceder a la instancia única de la clase.
     * @return la instancia única de la clase.
     */
    public static AudioManager getInstance() {
        return ourInstance;
    }

    /**
     * Añade al diccionario de audio un nuevo archivo de sonido.
     * @param filename el nombre y extensión del archivo de sonido (clave).
     * @param music el objeto de tipo Sound asociado al nombre de archivo proporcionado (valor).
     */
    public void addSound(String filename, Sound music) {
        sounds_.put(filename, music);
    }

    /**
     * Recupera un objeto de tipo Sound del diccioanrio de audio.
     * @param filename el nombre y extensión del archivo de sonido (clave).
     * @return el objeto de tipo Sound asociado al nombre de archivo proporcionado (valor).
     */
    public Sound getSound(String filename) {
        return sounds_.get(filename);
    }

    /**
     * Detiene la reproducción de todos los sonidos registrados en el diccionario de audio.
     */
    public void stopAllSounds() {
        for (Map.Entry mapElement : sounds_.entrySet()) {
            Sound s = (Sound) mapElement.getValue();
            s.stop();
        }
    }

    /**
     * Pone a 0 el volumen de todos los sonidos registrados en el diccionario de audio.
     */
    public void muteAllSounds() {
        for (Map.Entry mapElement : sounds_.entrySet()) {
            Sound s = (Sound) mapElement.getValue();
            s.setVolume(0.0f);
        }
    }

    /**
     * Restablece el volumen de todos los sonidos registrados en el diccionario de audio.
     */
    public void unmuteAllSounds() {
        for (Map.Entry mapElement : sounds_.entrySet()) {
            Sound s = (Sound) mapElement.getValue();
            s.setVolume(1.0f);
        }
    }
}
