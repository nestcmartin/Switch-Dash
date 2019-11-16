package es.ucm.vdm.engine.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

public class DesktopKeyboardHandler implements KeyListener {

    private boolean[] pressedKeys_ = new boolean[128];
    private Pool<Input.KeyEvent> keyEventPool_;
    private List<Input.KeyEvent> keyEventsBuffer_ = new ArrayList<>();
    private List<Input.KeyEvent> keyEvents_ = new ArrayList<>();

    public DesktopKeyboardHandler(JFrame window) {
        PoolObjectFactory<Input.KeyEvent> factory = new PoolObjectFactory<Input.KeyEvent>() {
            @Override
            public Input.KeyEvent createObject() {
                return new Input.KeyEvent();
            }
        };
        keyEventPool_ = new Pool<>(factory, 100);
        window.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        synchronized (this) {
            Input.KeyEvent keyEvent = keyEventPool_.newObject();
            keyEvent.keyCode_ = event.getKeyCode();
            keyEvent.keyChar_ = event.getKeyChar();
            if (event.getID() == KeyEvent.KEY_PRESSED) {
                keyEvent.type_ = Input.EventType.PRESSED;
                if (keyEvent.keyCode_ > 0 && keyEvent.keyCode_ < 127) {
                    pressedKeys_[keyEvent.keyCode_] = true;
                }
            }
            keyEventsBuffer_.add(keyEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        synchronized (this) {
            Input.KeyEvent keyEvent = keyEventPool_.newObject();
            keyEvent.keyCode_ = event.getKeyCode();
            keyEvent.keyChar_ = event.getKeyChar();
            if (event.getID() == KeyEvent.KEY_RELEASED) {
                keyEvent.type_ = Input.EventType.RELEASED;
                if (keyEvent.keyCode_ > 0 && keyEvent.keyCode_ < 127) {
                    pressedKeys_[keyEvent.keyCode_] = false;
                }
            }
            keyEventsBuffer_.add(keyEvent);
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }
        return pressedKeys_[keyCode];
    }

    public List<Input.KeyEvent> getKeyEvents() {
        synchronized (this) {
            for (int i = 0; i < keyEvents_.size(); i++) {
                keyEventPool_.free(keyEvents_.get(i));
            }
            keyEvents_.clear();
            keyEvents_.addAll(keyEventsBuffer_);
            keyEventsBuffer_.clear();
            return keyEvents_;
        }
    }
}
