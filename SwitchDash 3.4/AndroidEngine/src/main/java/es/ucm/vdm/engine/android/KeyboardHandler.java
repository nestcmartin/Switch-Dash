package es.ucm.vdm.engine.android;

import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utilities.Pool;
import es.ucm.vdm.engine.utilities.PoolObjectFactory;

public class KeyboardHandler implements View.OnKeyListener {

    private boolean[] pressedKeys_ = new boolean[128];
    private Pool<Input.KeyEvent> keyEventPool_;
    private List<Input.KeyEvent> keyEventsBuffer_ = new ArrayList<Input.KeyEvent>();
    private List<Input.KeyEvent> keyEvents_ = new ArrayList<Input.KeyEvent>();

    public KeyboardHandler(View view) {
        PoolObjectFactory<Input.KeyEvent> factory = new PoolObjectFactory<Input.KeyEvent>() {
            @Override
            public Input.KeyEvent createObject() {
                return new Input.KeyEvent();
            }
        };
        keyEventPool_ = new Pool<Input.KeyEvent>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
            return false;
        }

        synchronized (this) {
            Input.KeyEvent keyEvent = keyEventPool_.newObject();
            keyEvent.keyCode_ = keyCode;
            keyEvent.keyChar_ = (char) event.getUnicodeChar();
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                keyEvent.type_ = Input.KeyEvent.KEY_DOWN;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys_[keyCode] = true;
                }
            }
            if (event.getAction() == KeyEvent.ACTION_UP) {
                keyEvent.type_ = Input.KeyEvent.KEY_UP;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys_[keyCode] = false;
                }
            }
            keyEventsBuffer_.add(keyEvent);
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }
        return pressedKeys_[keyCode];
    }

    public List<Input.KeyEvent> getKeyEvents() {
        synchronized (this) {
            int len = keyEvents_.size();
            for (int i = 0; i < len; i++) {
                keyEventPool_.free(keyEvents_.get(i));
            }
            keyEvents_.clear();
            keyEvents_.addAll(keyEventsBuffer_);
            keyEventsBuffer_.clear();
            return keyEvents_;
        }
    }

}