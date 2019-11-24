package es.ucm.vdm.engine.android;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;

import java.io.IOException;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Sound;

/**
 * Implementación para Android del gestor de audio.
 */
public class AndroidAudio implements Audio {

    private AssetManager assets_;

    /**
     * Constructora de clase.
     * @param activity la actividad de Android.
     */
    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream((AudioManager.STREAM_MUSIC));
        this.assets_ = activity.getAssets();
    }

    /**
     * Devuelve un objeto de tipo AndroidSound.
     * @param fileName el nombre y extensión del archivo de sonido fuente.
     * @return un objeto de tipo AndroidSound.
     */
    @Override
    public Sound newSound(String fileName) {
        try {
            AssetFileDescriptor assetDescriptor = assets_.openFd(fileName);
            return new AndroidSound(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo de sonido '" + fileName +"'");
        }
    }
}