package es.ucm.vdm.engine.desktop;

import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;

public class DesktopInput implements Input {

    private DesktopKeyboardHandler keyHandler_;
    private DesktopMouseHandler mouseHandler_;

    public DesktopInput(JFrame window) {

        keyHandler_ = new DesktopKeyboardHandler(window);
        mouseHandler_ = new DesktopMouseHandler(window);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler_.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int id) {
        return mouseHandler_.isTouchDown(id);
    }

    @Override
    public int getTouchX(int id) {
        return mouseHandler_.getTouchX(id);
    }

    @Override
    public int getTouchY(int id) {
        return mouseHandler_.getTouchY(id);
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler_.getKeyEvents();
    }

    @Override
    public List<TouchEvent> getTouchEvents() { return  mouseHandler_.getTouchEventsBuffer_(); }
}
