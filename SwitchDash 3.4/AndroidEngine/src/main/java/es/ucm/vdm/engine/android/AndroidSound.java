package es.ucm.vdm.engine.android;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

import es.ucm.vdm.engine.Sound;

/**
 * Implementación para Android del wrapper de objetos de tipo sonido.
 */
public class AndroidSound implements Sound, MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer_;
    private boolean isPrepared_;

    /**
     * Constructora de clase.
     * @param assetDescriptor manejador de fichero de assets de Android.
     */
    public AndroidSound(AssetFileDescriptor assetDescriptor) {
        mediaPlayer_ = new MediaPlayer();
        try {
            mediaPlayer_.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer_.prepare();
            isPrepared_ = true;
            mediaPlayer_.setOnCompletionListener(this);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    /**
     * Reproduce un sonido.
     * Si ya se estaba reproduciendo, detiene la reproducción y la reinicia.
     * @param looping indica si el sonido debe reproducirse en bucle.
     */
    public void play(boolean looping) {
        if (isPlaying()) stop();
        try {
            synchronized (this) {
                if (!isPrepared_) mediaPlayer_.prepare();
                mediaPlayer_.start();
                setLooping(looping);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Detiene la reproducción del sonido.
     */
    public void stop() {
        mediaPlayer_.stop();
        synchronized (this) {
            isPrepared_ = false;
        }
    }

    /**
     * Pausa la reproducción de un sonido si se está reproduciendo.
     */
    @Override
    public void pause() {
        if (isPlaying()) mediaPlayer_.pause();
    }

    /**
     * Determina si un sonido se reproduce en bucle o no.
     * @param looping indica si el sonido debe reproducirse en bucle.
     */
    @Override
    public void setLooping(boolean looping) {
        mediaPlayer_.setLooping(looping);
    }

    /**
     * Determina el volumen de reproducción del sonido.
     * @param volume el volumen de reproducción deseado [0.0f-1.0f].
     */
    @Override
    public void setVolume(float volume) {
        mediaPlayer_.setVolume(volume, volume);
    }

    /**
     * @return true si el sonido se está reproduciendo, false en caso contrario.
     */
    @Override
    public boolean isPlaying() {
        return mediaPlayer_.isPlaying();
    }

    /**
     * @return true si el sonido no se está reproduciendo, false en caso contrario.
     */
    @Override
    public boolean isStopped() {
        return !isPrepared_;
    }

    /**
     * @return true si el sonido se está reproduciendo en bucle, false en caso contrario.
     */
    @Override
    public boolean isLooping() {
        return mediaPlayer_.isLooping();
    }

    /**
     * Libera los recursos de Android utilizados por el sonido.
     */
    @Override
    public void dispose() {
        if (isPlaying()) mediaPlayer_.stop();
        mediaPlayer_.release();
    }

    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared_ = false;
        }
    }

}