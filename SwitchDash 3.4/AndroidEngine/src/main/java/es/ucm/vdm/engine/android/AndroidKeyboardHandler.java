package es.ucm.vdm.engine.android;

import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

/**
 * Implementación para Android del gestor de eventos de teclado.
 */
public class AndroidKeyboardHandler implements View.OnKeyListener {

    private boolean[] pressedKeys_ = new boolean[128];
    private Pool<Input.KeyEvent> keyEventPool_;
    private List<Input.KeyEvent> keyEventsBuffer_ = new ArrayList<Input.KeyEvent>();
    private List<Input.KeyEvent> keyEvents_ = new ArrayList<Input.KeyEvent>();

    /**
     * Constructora de clase.
     * @param view la ventana de Android.
     */
    public AndroidKeyboardHandler(View view) {
        PoolObjectFactory<Input.KeyEvent> factory = new PoolObjectFactory<Input.KeyEvent>() {
            @Override
            public Input.KeyEvent createObject() {
                return new Input.KeyEvent();
            }
        };
        keyEventPool_ = new Pool<>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /**
     * Registra todos los eventos de teclado del dispositivo Android en un frame.
     * Cada evento de Android se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros de los eventos para su consulta directa y
     * se almacenan los eventos en la lista de eventos.
     * @param v la ventana de Android.
     * @param keyCode el código de la tecla.
     * @param event el evento de teclado de Android.
     * @return true si se produce un evento, false en caso contrario.
     */
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
            return false;
        }

        synchronized (this) {
            Input.KeyEvent keyEvent = keyEventPool_.newObject();
            keyEvent.keyCode_ = keyCode;
            keyEvent.keyChar_ = (char) event.getUnicodeChar();
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                keyEvent.type_ = Input.EventType.PRESSED;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys_[keyCode] = true;
                }
            }
            if (event.getAction() == KeyEvent.ACTION_UP) {
                keyEvent.type_ = Input.EventType.RELEASED;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys_[keyCode] = false;
                }
            }
            keyEventsBuffer_.add(keyEvent);
        }
        return false;
    }

    /**
     * Comprueba si una tecla está pulsada,
     * de entre las 127 disponibles en Android.
     * @param keyCode código de la tecla [0-127]
     * @return true si la tecla está pulsada, false en caso contrario.
     */
    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }
        return pressedKeys_[keyCode];
    }

    /**
     * Devuelve todos los eventos de teclado registrados en el frame actual.
     * Libera todos los eventos de teclado  registrados en el frame anterior.
     * @return la lista de todos los eventos de teclado en el último frame.
     */
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