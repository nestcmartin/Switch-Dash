package es.ucm.vdm.engine.desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;

public class DesktopMouseHandler implements MouseListener, MouseMotionListener {

    List<Input.TouchEvent> touchEventsBuffer_ = new ArrayList<Input.TouchEvent>();

    Input.TouchEvent[] currentTouchEventStates_ = new Input.TouchEvent[10];

    DesktopMouseHandler(JFrame window){
        window.addMouseListener(this);
        for (int i = 0; i < currentTouchEventStates_.length; i++)
            currentTouchEventStates_[i].id_ = i;

    }

    public List<Input.TouchEvent> getTouchEventsBuffer_(){
        List<Input.TouchEvent> touchEventListCopy = new ArrayList<Input.TouchEvent>();
        synchronized(this) {
            touchEventListCopy.addAll(touchEventsBuffer_);
            touchEventsBuffer_.clear();
        }
        return touchEventListCopy;
    }

    public boolean isTouchDown(int id) {
        return currentTouchEventStates_[id].type_ == Input.EventType.PRESSED;
    }

    public int getTouchX(int id) {
        return currentTouchEventStates_[id].x_;
    }

    public int getTouchY(int id) {
        return currentTouchEventStates_[id].y_;
    }

    private void updateCurrentState(Input.TouchEvent touchEvent){
        currentTouchEventStates_[touchEvent.id_].type_ = touchEvent.type_;
        currentTouchEventStates_[touchEvent.id_].x_ = touchEvent.x_;
        currentTouchEventStates_[touchEvent.id_].y_ = touchEvent.y_;
    }

    // MouseListener

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
        Input.TouchEvent tEvent = new Input.TouchEvent();
        tEvent.type_ = Input.EventType.PRESSED;
        tEvent.x_ = mouseEvent.getX();
        tEvent.y_ = mouseEvent.getY();
        tEvent.id_ = mouseEvent.getID();
        updateCurrentState(tEvent);
        synchronized(this) {
            touchEventsBuffer_.add(tEvent);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Input.TouchEvent tEvent = new Input.TouchEvent();
        tEvent.type_ = Input.EventType.RELEASED;
        tEvent.x_ = mouseEvent.getX();
        tEvent.y_ = mouseEvent.getY();
        tEvent.id_ = mouseEvent.getID();
        updateCurrentState(tEvent);
        synchronized(this) {
            touchEventsBuffer_.add(tEvent);
        }
    }

    // MouseMotionListener

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Input.TouchEvent tEvent = new Input.TouchEvent();
        tEvent.type_ = Input.EventType.MOVED;
        tEvent.x_ = mouseEvent.getX();
        tEvent.y_ = mouseEvent.getY();
        tEvent.id_ = mouseEvent.getID();
        updateCurrentState(tEvent);
        synchronized(this) {
            touchEventsBuffer_.add(tEvent);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }
}
