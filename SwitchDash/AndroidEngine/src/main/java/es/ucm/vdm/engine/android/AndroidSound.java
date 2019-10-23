package es.ucm.vdm.engine.android;

import android.media.SoundPool;

import es.ucm.vdm.engine.Sound;

public class AndroidSound implements Sound {

    int soundId_;
    SoundPool soundPool_;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId_ = soundId;
        this.soundPool_ = soundPool;
    }

    public void play(float volume) {
        soundPool_.play(soundId_, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool_.unload(soundId_);
    }

}
