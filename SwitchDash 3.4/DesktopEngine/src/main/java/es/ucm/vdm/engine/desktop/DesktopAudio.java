package es.ucm.vdm.engine.desktop;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Sound;

public class DesktopAudio implements Audio {

    public DesktopAudio() {
    }

    @Override
    public Sound newSound(String filename) {

        Clip clip = null;
        boolean prepared = false;

        try {
            File file = new File("assets/" + filename);
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
