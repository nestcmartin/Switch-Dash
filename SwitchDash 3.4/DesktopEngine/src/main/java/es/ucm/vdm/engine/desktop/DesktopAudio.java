package es.ucm.vdm.engine.desktop;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Music;
import es.ucm.vdm.engine.Sound;

public class DesktopAudio implements Audio {


    public DesktopAudio() {
    }

    @Override
    public Music newMusic(String filename) {

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

        return new DesktopMusic(clip);
    }

    @Override
    public Sound newSound(String filename) {

        AudioClip clip = null;

        try {
            File file = new File("assets/" + filename);
            URL url = file.toURI().toURL();
            clip = Applet.newAudioClip(url);
        } catch (MalformedURLException e) {
            System.err.println("Could not load '" + filename + "'");
        } catch (IllegalArgumentException e) {
            System.err.println("Could not load '" + filename + "'");
        }

        return new DesktopSound(clip);
    }

}
