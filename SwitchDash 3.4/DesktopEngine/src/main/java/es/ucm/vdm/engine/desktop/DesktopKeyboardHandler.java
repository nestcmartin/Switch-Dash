package es.ucm.vdm.engine.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

/**
 * Implementación para Desktop del gestor de eventos de teclado.
 * Implementa KeyListener de java.
 */
public class DesktopKeyboardHandler implements KeyListener {

    private boolean[] pressedKeys_ = new boolean[128];
    private Pool<Input.KeyEvent> keyEventPool_;
    private List<Input.KeyEvent> keyEventsBuffer_ = new ArrayList<>();
    private List<Input.KeyEvent> keyEvents_ = new ArrayList<>();

    /**
     * Constructora de clase.
     * @param window la ventana de JFrame.
     */
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

    /**
     * Registra un evento de teclado cuando una tecla ha sido pulsada.
     * Cada evento KeyEvent de java se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros de los eventos para su consulta directa y
     * se almacenan los eventos en la lista de eventos.
     * @param event el evento de teclado de java (KeyEvent).
     */
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

    /**
     * Registra un evento de teclado cuando una tecla ha sido soltada.
     * Cada evento KeyEvent de java se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros de los eventos para su consulta directa y
     * se almacenan los eventos en la lista de eventos.
     * @param event el evento de teclado de java (KeyEvent).
     */
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

    /**
     * Comprueba si una tecla está pulsada,
     * de entre las 127 disponibles en la tabla ascii.
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
            for (int i = 0; i < keyEvents_.size(); i++) {
                keyEventPool_.free(keyEvents_.get(i));
            }
            keyEvents_.clear();
            keyEvents_.addAll(keyEventsBuffer_);
            keyEventsBuffer_.clear();
            return keyEvents_;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {}
}
