package es.ucm.vdm.engine.android;

import android.view.View;

import java.util.List;

import es.ucm.vdm.engine.Input;

public class AndroidInput implements Input {

    private AndroidKeyboardHandler keyHandler_;
    private AndroidTouchHandler touchHandler_;

    public AndroidInput(View view, float scaleX, float scaleY) {
        keyHandler_ = new AndroidKeyboardHandler(view);
        touchHandler_ = new AndroidMultiTouchHandler(view, scaleX, scaleY);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyHandler_.isKeyPressed(keyCode);
    }

    public boolean isTouchDown(int pointer) {
        return touchHandler_.isTouchDown(pointer);
    }

    public int getTouchX(int pointer) {
        return touchHandler_.getTouchX(pointer);
    }

    public int getTouchY(int pointer) {
        return touchHandler_.getTouchY(pointer);
    }

    public List<KeyEvent> getKeyEvents() {
        return keyHandler_.getKeyEvents();
    }

    public List<TouchEvent> getTouchEvents() {
        return touchHandler_.getTouchEvents();
    }
}