package es.ucm.vdm.engine.desktop;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Sound;

/**
 * Implementación para Desktop del gestor de audio.
 */
public class DesktopAudio implements Audio {

    /**
     * Constructora de clase.
     */
    public DesktopAudio() {}

    /**
     * Devuelve un objeto de tipo DesktopSound.
     * @param fileName el nombre y extensión del archivo de sonido fuente.
     * @return un objeto de tipo DesktopSound.
     */
    @Override
    public Sound newSound(String fileName) {

        Clip clip = null;
        boolean prepared = false;

        try {
            File file = new File("assets/" + fileName);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            try {
                clip.open(audioInput);
                prepared = true;
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DesktopSound(clip);
    }
}
