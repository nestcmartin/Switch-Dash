package es.ucm.vdm.engine.android;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.FileIO;
import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.Screen;

public class AndroidGame extends Activity implements Game {

    private AndroidFastRenderView renderView_;
    private Graphics graphics_;
    private Audio audio_;
    private Input input_;
    private FileIO fileIO_;
    private Screen screen_;

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
        fileIO_ = new AndroidFileIO(this);
        audio_ = new AndroidAudio(this);
        input_ = new AndroidInput(renderView_, scaleX, scaleY);
        screen_ = getStartScreen();
        setContentView(renderView_);
    }

    @Override
    public void onResume() {
        super.onResume();
        screen_.resume();
        renderView_.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView_.pause();
        screen_.pause();

        if (isFinishing())
        {
            screen_.dispose();
        }
    }


    public Input getInput() {
        return input_;
    }

    public FileIO getFileIO() {
        return fileIO_;
    }

    public Graphics getGraphics() {
        return graphics_;
    }

    public Audio getAudio() {
        return audio_;
    }

    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.screen_.pause();
        this.screen_.dispose();
        screen.resume();
        screen.update(0);
        this.screen_ = screen;
    }

    public Screen getCurrentScreen() {
        return screen_;
    }

    public Screen getStartScreen() {
        return null;
    }
}