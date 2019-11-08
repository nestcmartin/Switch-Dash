package es.ucm.vdm.engine.desktop;

import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;

public class DesktopInput implements Input {

    private DesktopKeyboardHandler keyHandler_;

    public DesktopInput(JFrame window) {
        keyHandler_ = new DesktopKeyboardHandler(window);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return false;
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return false;
    }

    @Override
    public int getTouchX(int pointer) {
        return 0;
    }

    @Override
    public int getTouchY(int pointer) {
        return 0;
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        return null;
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }
}
