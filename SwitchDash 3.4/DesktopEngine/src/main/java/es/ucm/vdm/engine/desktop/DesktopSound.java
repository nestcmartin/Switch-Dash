package es.ucm.vdm.engine.desktop;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import es.ucm.vdm.engine.Sound;

/**
 * Implementación para Desktop del wrapper de objetos de tipo sonido.
 */
public class DesktopSound implements Sound {

    Clip clip_;
    private long clipTimePosition_ = 0;
    private boolean isPlaying_ = false;
    private boolean isLooping_ = false;

    /**
     * Constructora de clase.
     * @param clip clip de sonido de java.
     */
    public DesktopSound(Clip clip) {
        this.clip_ = clip;
    }

    /**
     * Reproduce un sonido.
     * Si ya se estaba reproduciendo, detiene la reproducción y la reinicia.
     * @param looping indica si el sonido debe reproducirse en bucle.
     */
    @Override
    public void play(boolean looping) {
        clip_.setMicrosecondPosition(clipTimePosition_);
        clip_.start();
        setLooping(looping);

        clipTimePosition_ = 0;
        isPlaying_ = true;
    }

    /**
     * Detiene la reproducción del sonido.
     */
    @Override
    public void stop() {
        clipTimePosition_ = 0;
        clip_.stop();

        isPlaying_ = false;
    }

    /**
     * Pausa la reproducción de un sonido si se está reproduciendo.
     */
    @Override
    public void pause() {
        clipTimePosition_ = clip_.getMicrosecondPosition();
        clip_.stop();

        isPlaying_ = false;
    }

    /**
     * Determina si un sonido se reproduce en bucle o no.
     * @param looping indica si el sonido debe reproducirse en bucle.
     */
    @Override
    public void setLooping(boolean looping) {
        isLooping_= looping;
        if (looping) clip_.loop(Clip.LOOP_CONTINUOUSLY);
        else clip_.loop(0);
    }

    /**
     * Determina el volumen de reproducción del sonido.
     * @param volume el volumen de reproducción deseado [0.0f-1.0f].
     */
    @Override
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip_.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * @return true si el sonido se está reproduciendo, false en caso contrario.
     */
    @Override
    public boolean isPlaying() {
        return isPlaying_;
    }

    /**
     * @return true si el sonido no se está reproduciendo, false en caso contrario.
     */
    @Override
    public boolean isStopped() {
        return clipTimePosition_ == 0;
    }

    /**
     * @return true si el sonido se está reproduciendo en bucle, false en caso contrario.
     */
    @Override
    public boolean isLooping() {
        return isLooping_;
    }

    @Override
    public void dispose() {}
}
