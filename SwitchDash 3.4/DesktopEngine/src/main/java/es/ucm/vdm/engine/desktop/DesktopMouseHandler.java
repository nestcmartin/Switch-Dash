package es.ucm.vdm.engine.desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;

public class DesktopMouseHandler implements MouseListener {

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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Input.TouchEvent tEvent = new Input.TouchEvent();
        tEvent.type_ = Input.EventType.PRESSED;
        tEvent.x_ = e.getX();
        tEvent.y_ = e.getY();
        synchronized(this) {
            touchEventsBuffer_.add(tEvent);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Input.TouchEvent tEvent = new Input.TouchEvent();
        tEvent.type_ = Input.EventType.RELEASED;
        tEvent.x_ = e.getX();
        tEvent.y_ = e.getY();
        synchronized(this) {
            touchEventsBuffer_.add(tEvent);
        }
    }

}
