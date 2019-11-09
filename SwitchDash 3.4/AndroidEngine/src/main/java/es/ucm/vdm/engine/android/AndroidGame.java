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

    private AndroidRenderView renderView_;
    private AndroidGraphics graphics_;
    private AndroidInput input_;
    private State state_;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        state_ = getStartState();

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

    public State getCurrentState() {
        return state_;
    }

    public State getStartState() {
        return null;
    }
}