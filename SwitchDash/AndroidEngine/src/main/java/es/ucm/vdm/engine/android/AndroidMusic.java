package es.ucm.vdm.engine.android;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

import es.ucm.vdm.engine.Music;

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener {

    MediaPlayer mediaPlayer_;
    boolean isPrepared_;

    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
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

    public void dispose() {
        if (isPlaying()) mediaPlayer_.stop();
        mediaPlayer_.release();
    }

    public boolean isLooping() {
        return mediaPlayer_.isLooping();
    }

    public boolean isPlaying() {
        return mediaPlayer_.isPlaying();
    }

    public boolean isStopped() {
        return !isPrepared_;
    }

    public void pause() {
        if (isPlaying()) mediaPlayer_.pause();
    }

    public void play() {
        if (isPlaying()) return;
        try {
            synchronized (this) {
                if (!isPrepared_) mediaPlayer_.prepare();
                mediaPlayer_.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLooping(boolean isLooping) {
        mediaPlayer_.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        mediaPlayer_.setVolume(volume, volume);
    }

    public void stop() {
        mediaPlayer_.stop();
        synchronized (this) {
            isPrepared_ = false;
        }
    }

    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared_ = false;
        }
    }

}