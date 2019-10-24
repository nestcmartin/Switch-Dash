package es.ucm.vdm.engine.android;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Music;
import es.ucm.vdm.engine.Sound;

public class AndroidAudio implements Audio {

    private AssetManager assets_;
    private SoundPool soundPool_;

    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream((AudioManager.STREAM_MUSIC));
        this.assets_ = activity.getAssets();
        this.soundPool_ = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }

    public Music newMusic(String fileName) {
        try {
            AssetFileDescriptor assetDescriptor = assets_.openFd(fileName);
            return new AndroidMusic(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + fileName +"'");
        }
    }

    public Sound newSound(String fileName) {
        try {
            AssetFileDescriptor assetDescriptor = assets_.openFd(fileName);
            int soundId = soundPool_.load(assetDescriptor, 0);
            return new AndroidSound(soundPool_, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + fileName +"'");
        }
    }
}