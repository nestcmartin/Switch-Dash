package es.ucm.vdm.engine.desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

public class DesktopMouseHandler implements MouseListener, MouseMotionListener {

    private boolean isTouched_;
    private int touchX_;
    private int touchY_;

    private Pool<Input.TouchEvent> touchEventPool_;
    private List<Input.TouchEvent> touchEvents_ = new ArrayList<Input.TouchEvent>();
    private List<Input.TouchEvent> touchEventsBuffer_ = new ArrayList<Input.TouchEvent>();


    DesktopMouseHandler(JFrame window) {
        PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        touchEventPool_ = new Pool<Input.TouchEvent>(factory, 100);
        window.addMouseListener(this);
    }

    public boolean isTouchDown() {
        return isTouched_;
    }

    public int getTouchX() {
        return touchX_;
    }

    public int getTouchY() {
        return touchY_;
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


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        synchronized (this) {
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            touchEvent.type_ = Input.EventType.PRESSED;
            touchEvent.id_ = mouseEvent.getButton();
            touchEvent.x_ = touchX_ = mouseEvent.getX();
            touchEvent.y_ = touchY_ = mouseEvent.getY();
            touchEventsBuffer_.add(touchEvent);
            isTouched_ = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        synchronized (this) {
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            touchEvent.type_ = Input.EventType.RELEASED;
            touchEvent.id_ = mouseEvent.getButton();
            touchEvent.x_ = touchX_ = mouseEvent.getX();
            touchEvent.y_ = touchY_ = mouseEvent.getY();
            touchEventsBuffer_.add(touchEvent);
            isTouched_ = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        synchronized (this) {
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            touchEvent.type_ = Input.EventType.MOVED;
            touchEvent.id_ = mouseEvent.getButton();
            touchEvent.x_ = touchX_ = mouseEvent.getX();
            touchEvent.y_ = touchY_ = mouseEvent.getY();
            touchEventsBuffer_.add(touchEvent);
            isTouched_ = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

}
