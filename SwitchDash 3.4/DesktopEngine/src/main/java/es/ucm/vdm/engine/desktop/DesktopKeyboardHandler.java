package es.ucm.vdm.engine.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;

public class DesktopKeyboardHandler implements KeyListener {

    private List<Input.KeyEvent> keyEventsBuffer_ = new ArrayList<>();
    private List<Input.KeyEvent> keyEvents_ = new ArrayList<>();

    public DesktopKeyboardHandler(JFrame window) {
        window.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
