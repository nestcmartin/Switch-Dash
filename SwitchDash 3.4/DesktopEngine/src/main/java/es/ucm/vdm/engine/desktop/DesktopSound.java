package es.ucm.vdm.engine.desktop;


import java.applet.AudioClip;

import es.ucm.vdm.engine.Sound;

public class DesktopSound implements Sound {

    AudioClip clip_;

    public DesktopSound(AudioClip clip) {
        this.clip_ = clip;
    }

    @Override
    public void play(float volume) {
        clip_.play();
    }

    @Override
    public void dispose() {
        clip_.stop();
    }
}
