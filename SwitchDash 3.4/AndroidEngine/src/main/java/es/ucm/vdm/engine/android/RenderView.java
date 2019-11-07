package es.ucm.vdm.engine.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RenderView extends SurfaceView implements Runnable {

    private AndroidGame game_;
    private Bitmap frameBuffer_;
    private Thread renderThread_ = null;
    private SurfaceHolder holder_;
    private volatile boolean running_ = false;

    public RenderView(AndroidGame game, Bitmap frameBuffer) {
        super(game);
        this.game_ = game;
        this.frameBuffer_ = frameBuffer;
        this.holder_ = getHolder();
    }

    public void resume() {
        running_ = true;
        renderThread_ = new Thread(this);
        renderThread_.start();
    }

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

            Canvas canvas = holder_.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer_, null, dstRect, null);
            holder_.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running_ = false;
        while(true) {
            try {
                renderThread_.join();
                return;
            } catch (InterruptedException e) {

            }
        }
    }
}
