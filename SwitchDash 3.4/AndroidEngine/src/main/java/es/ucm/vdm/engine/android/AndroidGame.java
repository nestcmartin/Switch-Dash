package es.ucm.vdm.engine.android;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.State;

public class AndroidGame extends Activity implements Game {

    private AndroidFastRenderView renderView_;
    private Graphics graphics_;
    //private Audio audio_;
    private Input input_;
    //private FileIO fileIO_;
    private State state_;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        boolean isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Bitmap.Config.RGB_565);
        float scaleX = (float)frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float)frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();
        renderView_ = new AndroidFastRenderView(this, frameBuffer);
        graphics_ = new AndroidGraphics(getAssets(), frameBuffer);
        //fileIO_ = new AndroidFileIO(this);
        //audio_ = new AndroidAudio(this);
        input_ = new AndroidInput(renderView_, scaleX, scaleY);
        state_ = getStartScreen();
        setContentView(renderView_);
    }

    @Override
    public void onResume() {
        super.onResume();
        state_.resume();
        renderView_.resume();
    }

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


    public Input getInput() {
        return input_;
    }

    public Graphics getGraphics() {
        return graphics_;
    }

    public void setScreen(State screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.state_.pause();
        this.state_.dispose();
        screen.resume();
        screen.update(0);
        this.state_ = screen;
    }

    public State getCurrentScreen() {
        return state_;
    }

    public State getStartScreen() {
        return null;
    }
}