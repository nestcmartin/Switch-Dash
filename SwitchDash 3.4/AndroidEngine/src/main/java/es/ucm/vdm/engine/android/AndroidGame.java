package es.ucm.vdm.engine.android;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utils.AudioManager;

/**
 * Implementación para Android del juego.
 */
public class AndroidGame extends Activity implements Game {

    private AndroidRenderView renderView_;
    private AndroidGraphics graphics_;
    private AndroidInput input_;
    private AndroidAudio audio_;
    private State state_;

    /**
     * Método que se llama al iniciar la Actividad.
     * Se establecen los parámetros de la ventana de Android.
     * Se establece la escala de detección de input en función del tamaño del dispositivo.
     * Se inicializan todos los sitemas del motor.
     * @param savedInstance instancia de la Actividad de Android.
     */
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 1920 : 1080;
        int frameBufferHeight = isLandscape ? 1080 : 1920;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);
        float scaleX = (float)frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float)frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        renderView_ = new AndroidRenderView(this, frameBuffer);
        graphics_ = new AndroidGraphics(getAssets(), frameBuffer);
        input_ = new AndroidInput(renderView_, scaleX, scaleY);
        audio_ = new AndroidAudio(this);
        state_ = getStartState();

        setContentView(renderView_);
    }

    /**
     * Método que se llama al reanudar la actividad.
     * Se llama al método de reanudación del estado actual
     * y se reanuda la ejecución del hilo de renderizado.
     */
    @Override
    public void onResume() {
        super.onResume();
        state_.resume();
        renderView_.resume();
    }

    /**
     * Método que se llama al pausar la actividad.
     * Se pausa la ejecución del hilo de renderizado
     * y se llama al método de pausa del estado actual.
     * Si se va a cerrar la actividad, se llama al método
     * de liberación de recursos del estado actual.
     */
    @Override
    public void onPause() {
        super.onPause();
        renderView_.pause();
        state_.pause();

        if (isFinishing())
        {
            state_.dispose();
        }
    }

    /**
     * Método que se llama al detener la actividad.
     * Se utiliza para detener la reproducción de todos los sonidos de la aplicación.
     */
    @Override
    protected void onStop() {
        super.onStop();
        AudioManager.getInstance().stopAllSounds();
    }


    /**
     * @return el gestor de input del juego.
     */
    @Override
    public Input getInput() {
        return input_;
    }

    /**
     * @return el gestor de audio del juego.
     */
    @Override
    public Audio getAudio() {
        return audio_;
    }

    /**
     * @return el gestor gráfico del juego.
     */
    @Override
    public ScaledGraphics getGraphics() {
        return graphics_;
    }

    /**
     * Gestiona el cambio de un estado a otro.
     * Pausa el estado actual y lo libera, para luego
     * inicializar el nuevo estado y establecerlo como el actual.
     * @param state el nuevo estado del juego.
     */
    @Override
    public void setState(State state) {
        if (state == null) {
            throw new IllegalArgumentException("State must not be null");
        }

        this.state_.pause();
        this.state_.dispose();
        state.resume();
        state.update(0);
        this.state_ = state;
    }

    /**
     * @return el estado actual del juego.
     */
    @Override
    public State getCurrentState() {
        return state_;
    }

    /**
     * Método a implementar por las aplicaciones finales.
     * Normalmente este estado define el punto de entrada al juego.
     * @return el estado inicial del juego.
     */
    @Override
    public State getStartState() {
        return null;
    }
}