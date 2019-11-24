package es.ucm.vdm.engine.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Gestor de la ventana de Android y del bucle principal de juego en Android.
 */
public class AndroidRenderView extends SurfaceView implements Runnable {

    private AndroidGame game_;
    private Bitmap frameBuffer_;
    private Thread renderThread_ = null;
    private SurfaceHolder holder_;
    private volatile boolean running_ = false;

    /**
     * Constructora de clase.
     * @param game referencia al juego de Android que gestiona el bucle.
     * @param frameBuffer la superficie de renderizado de la ventana.
     */
    public AndroidRenderView(AndroidGame game, Bitmap frameBuffer) {
        super(game);
        this.game_ = game;
        this.frameBuffer_ = frameBuffer;
        this.holder_ = getHolder();
    }

    /**
     * Método que gestiona el bucle principal de juego.
     * Calcula el delta time y se encarga de actualizar la lógica y el renderizado del juego.
     * Si el dispositivo lo permite, utilizará un canvas acelerado por hardware.
     */
    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running_) {
            if (!holder_.getSurface().isValid()) {
                continue;
            }

            double deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game_.getCurrentState().update(deltaTime);
            game_.getCurrentState().render(deltaTime);

            Canvas canvas = null;
            if (android.os.Build.VERSION.SDK_INT >= 26){
                canvas = holder_.lockHardwareCanvas();
            } else {
                canvas = holder_.lockCanvas();
            }

            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer_, null, dstRect, null);
            holder_.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Método que se llama justo antes de suspender el bucle principal.
     */
    public void pause() {
        running_ = false;
        while(true) {
            try {
                renderThread_.join();
                return;
            } catch(InterruptedException e) {

            }
        }
    }

    /**
     * Método que se llama justo después de reanudar el bucle principal.
     */
    public void resume() {
        running_ = true;
        renderThread_ = new Thread(this);
        game_.getGraphics().scaleCanvas();
        renderThread_.start();
    }
}
