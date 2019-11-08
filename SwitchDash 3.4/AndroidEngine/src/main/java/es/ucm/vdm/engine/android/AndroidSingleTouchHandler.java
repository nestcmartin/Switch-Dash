package es.ucm.vdm.engine.android;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utilities.Pool;
import es.ucm.vdm.engine.utilities.PoolObjectFactory;

public class AndroidSingleTouchHandler implements AndroidTouchHandler {

    private boolean isTouched_;
    private int touchX_;
    private int touchY_;
    private Pool<Input.TouchEvent> touchEventPool_;
    private List<Input.TouchEvent> touchEvents_ = new ArrayList<Input.TouchEvent>();
    private List<Input.TouchEvent> touchEventsBuffer_ = new ArrayList<Input.TouchEvent>();
    private float scaleX_;
    private float scaleY_;

    public AndroidSingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        touchEventPool_ = new Pool<Input.TouchEvent>(factory, 100);
        view.setOnTouchListener(this);
        this.scaleX_ = scaleX;
        this.scaleY_ = scaleY;
    }

    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type_ = Input.TouchEvent.TOUCH_DOWN;
                    isTouched_ = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type_ = Input.TouchEvent.TOUCH_DRAGGED;
                    isTouched_ = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type_ = Input.TouchEvent.TOUCH_UP;
                    isTouched_ = false;
                    break;
            }
            touchEvent.x_ = touchX_ = (int)(event.getX() * scaleX_);
            touchEvent.y_ = touchY_ = (int)(event.getY() * scaleY_);
            touchEventsBuffer_.add(touchEvent);
            return true;
        }
    }

    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if(pointer == 0) return isTouched_;
            else return false;
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            return touchX_;
        }
    }

    public int getTouchY(int pointer) {
        synchronized (this) {
            return touchY_;
        }
    }

    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents_.size();
            for(int i = 0; i < len; i++) {
                touchEventPool_.free(touchEvents_.get(i));
            }
            touchEvents_.clear();
            touchEvents_.addAll(touchEventsBuffer_);
            touchEventsBuffer_.clear();
            return touchEvents_;
        }
    }

}