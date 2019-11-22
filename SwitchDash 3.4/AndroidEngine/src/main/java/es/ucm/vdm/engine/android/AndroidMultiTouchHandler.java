package es.ucm.vdm.engine.android;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

public class AndroidMultiTouchHandler implements AndroidTouchHandler {

    private static final int MAX_TOUCHPOINTS = 10;

    private boolean[] isTouched_ = new boolean[MAX_TOUCHPOINTS];
    private int[] touchX_ = new int[MAX_TOUCHPOINTS];
    private int[] touchY_ = new int[MAX_TOUCHPOINTS];
    private int[] id_ = new int[MAX_TOUCHPOINTS];
    private Pool<Input.TouchEvent> touchEventPool_;
    private List<Input.TouchEvent> touchEvents_ = new ArrayList<>();
    private List<Input.TouchEvent> touchEventsBuffer_ = new ArrayList<>();
    private float scaleX_;
    private float scaleY_;

    public AndroidMultiTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {
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
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >>
                                                    MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerCount = event.getPointerCount();
            Input.TouchEvent touchEvent;
            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i >= pointerCount) {
                    isTouched_[i] = false;
                    id_[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
                    continue;
                }
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool_.newObject();
                        touchEvent.type_ = Input.EventType.PRESSED;
                        touchEvent.id_ = pointerId;
                        touchEvent.x_ = touchX_[i] = (int) (event.getX(i) * scaleX_);
                        touchEvent.y_ = touchY_[i] = (int) (event.getY(i) * scaleY_);
                        isTouched_[i] = true;
                        id_[i] = pointerId;
                        touchEventsBuffer_.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent = touchEventPool_.newObject();
                        touchEvent.type_ = Input.EventType.RELEASED;
                        touchEvent.id_ = pointerId;
                        touchEvent.x_ = touchX_[i] = (int) (event.getX(i) * scaleX_);
                        touchEvent.y_ = touchY_[i] = (int) (event.getY(i) * scaleY_);
                        isTouched_[i] = false;
                        id_[i] = -1;
                        touchEventsBuffer_.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool_.newObject();
                        touchEvent.type_ = Input.EventType.MOVED;
                        touchEvent.id_ = pointerId;
                        touchEvent.x_ = touchX_[i] = (int) (event.getX(i) * scaleX_);
                        touchEvent.y_ = touchY_[i] = (int) (event.getY(i) * scaleY_);
                        isTouched_[i] = true;
                        id_[i] = pointerId;
                        touchEventsBuffer_.add(touchEvent);
                        break;
                }
            }
            return true;
        }
    }

    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return false;
            else
                return isTouched_[index];
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX_[index];
        }
    }
    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY_[index];
        }
    }

    // returns the index for a given pointerId or -1 if no index.
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id_[i] == pointerId) {
                return i;
            }
        }
        return -1;
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
