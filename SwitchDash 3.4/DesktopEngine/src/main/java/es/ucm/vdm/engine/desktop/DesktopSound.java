package es.ucm.vdm.engine.desktop;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import es.ucm.vdm.engine.Sound;

public class DesktopSound implements Sound {

    Clip clip_;
    private long clipTimePosition_ = 0;
    private boolean isPlaying_ = false;
    private boolean isLooping_ = false;

    public DesktopSound(Clip clip) {
        this.clip_ = clip;
    }

    @Override
    public void play() {
        clip_.setMicrosecondPosition(clipTimePosition_);
        clip_.start();

        clipTimePosition_ = 0;
        isPlaying_ = true;
    }

    @Override
    public void stop() {
        clipTimePosition_ = 0;
        clip_.stop();

        isPlaying_ = false;
    }

    @Override
    public void pause() {
        clipTimePosition_ = clip_.getMicrosecondPosition();
        clip_.stop();

        isPlaying_ = false;
    }

    @Override
    public void setLooping(boolean looping) {
        isLooping_= looping;
        if (looping) clip_.loop(Clip.LOOP_CONTINUOUSLY);
        else clip_.loop(0);
    }

    @Override
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip_.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    @Override
    public boolean isPlaying() {
        return isPlaying_;
    }

    @Override
    public boolean isStopped() {
        return clipTimePosition_ == 0;
    }

    @Override
    public boolean isLooping() {
        return isLooping_;
    }

    @Override
    public void dispose() {

    }
}
