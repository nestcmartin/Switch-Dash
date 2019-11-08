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

    DesktopMouseHandler(JFrame window){
        window.addMouseListener(this);
    }

    public List<Input.TouchEvent> getTouchEventsBuffer_(){
        List<Input.TouchEvent> touchEventListCopy = new ArrayList<Input.TouchEvent>();
        synchronized(this) {
            touchEventListCopy.addAll(touchEventsBuffer_);
            touchEventsBuffer_.clear();
        }
        return touchEventListCopy;
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
        synchronized(this) {
            touchEventsBuffer_.add(tEvent);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }
}
