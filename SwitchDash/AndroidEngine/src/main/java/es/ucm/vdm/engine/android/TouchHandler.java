package es.ucm.vdm.engine.android;

import android.view.View;

import java.util.List;

import es.ucm.vdm.engine.Input;

public interface TouchHandler extends View.OnTouchListener {

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);
    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();

}
