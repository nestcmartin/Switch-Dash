package es.ucm.vdm.engine.desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

/**
 * Implementación para Desktop del gestor de eventos de ratón.
 * Implementa MouseListener y MouseMotionListener de java.
 */
public class DesktopMouseHandler implements MouseListener, MouseMotionListener {

    private static final int MAX_TOUCHPOINTS = 10;

    boolean[] isTouched_ = new boolean[MAX_TOUCHPOINTS];
    int[] touchX_ = new int[MAX_TOUCHPOINTS];
    int[] touchY_ = new int[MAX_TOUCHPOINTS];
    int[] id_ = new int[MAX_TOUCHPOINTS];

    private Pool<Input.TouchEvent> touchEventPool_;
    private List<Input.TouchEvent> touchEvents_ = new ArrayList<Input.TouchEvent>();
    private List<Input.TouchEvent> touchEventsBuffer_ = new ArrayList<Input.TouchEvent>();

    /**
     * Constructora de clase.
     * Se añade a si mismo como MouseListener a la ventana de JFrame.
     * @param window la ventana de JFrame.
     */
    DesktopMouseHandler(JFrame window) {
        PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        touchEventPool_ = new Pool<Input.TouchEvent>(factory, 100);
        window.addMouseListener(this);
    }

    /**
     * Comprueba si el botón del ratón con el id indicado está pulsado.
     * @param buttonId id del botón del ratón que se quiere consultar.
     * @return true si el botón está pulsado, false en caso contrario.
     */
    public boolean isTouchDown(int buttonId) {
        synchronized (this) {
            if (buttonId < 0 || buttonId >= MAX_TOUCHPOINTS)
                return false;
            else
                return isTouched_[buttonId];
        }
    }

    /**
     * Devuelve la coordenada x del botón del ratón con el id indicado.
     * @param buttonId id del botón del ratón que se quiere consultar.
     * @return la coordenada x si el botón está pulsado, -1 en caso contrario.
     */
    public int getTouchX(int buttonId) {
        synchronized (this) {
            if (buttonId < 0 || buttonId >= MAX_TOUCHPOINTS)
                return -1;
            else
                return touchX_[buttonId];
        }
    }

    /**
     * Devuelve la coordenada y del botón del ratón con el id indicado.
     * @param buttonId id del botón del ratón que se quiere consultar.
     * @return la coordenada y si el botón está pulsado, -1 en caso contrario.
     */
    public int getTouchY(int buttonId) {
        synchronized (this) {
            if (buttonId < 0 || buttonId >= MAX_TOUCHPOINTS)
                return -1;
            else
                return touchY_[buttonId];
        }
    }

    /**
     * Devuelve todos los eventos de ratón registrados en el frame actual.
     * Libera todos los eventos de ratón  registrados en el frame anterior.
     * @return la lista de todos los eventos de ratón en el último frame.
     */
    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents_.size();
            for(int i = 0; i < len; i++) {
                touchEventPool_.free(touchEvents_.get(i));
            }
            touchEvents_.clear();
            touchEvents_.addAll(touchEventsBuffer_);
            touchEventsBuffer_.clear();
            return touchEvents_;
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    /**
     * Registra un evento de ratón cuando un botón ha sido pulsado.
     * Cada evento MouseEvent de java se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros de los eventos para su consulta directa y
     * se almacenan los eventos en la lista de eventos.
     * @param mouseEvent el evento de ratón de java (MouseEvent).
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        synchronized (this) {
            int buttonId = mouseEvent.getButton();
            if (buttonId < 0 || buttonId >= MAX_TOUCHPOINTS) return;
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            touchEvent.type_ = Input.EventType.PRESSED;
            touchEvent.id_ = buttonId;
            touchEvent.x_ = touchX_[buttonId] = mouseEvent.getX();
            touchEvent.y_ = touchY_[buttonId] = mouseEvent.getY();
            touchEventsBuffer_.add(touchEvent);
            isTouched_[buttonId] = true;
        }
    }

    /**
     * Registra un evento de ratón cuando un botón ha sido soltado.
     * Cada evento MouseEvent de java se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros de los eventos para su consulta directa y
     * se almacenan los eventos en la lista de eventos.
     * @param mouseEvent el evento de ratón de java (MouseEvent).
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        synchronized (this) {
            int buttonId = mouseEvent.getButton();
            if (buttonId < 0 || buttonId >= MAX_TOUCHPOINTS) return;
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            touchEvent.type_ = Input.EventType.RELEASED;
            touchEvent.id_ = buttonId;
            touchEvent.x_ = touchX_[buttonId] = mouseEvent.getX();
            touchEvent.y_ = touchY_[buttonId] = mouseEvent.getY();
            touchEventsBuffer_.add(touchEvent);
            isTouched_[buttonId] = false;
        }
    }

    /**
     * Registra un evento de ratón cuando este se ha movido mientras estaba pulsado.
     * Cada evento MouseEvent de java se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros de los eventos para su consulta directa y
     * se almacenan los eventos en la lista de eventos.
     * @param mouseEvent el evento de ratón de java (MouseEvent).
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        synchronized (this) {
            int buttonId = mouseEvent.getButton();
            if (buttonId < 0 || buttonId >= MAX_TOUCHPOINTS) return;
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            touchEvent.type_ = Input.EventType.MOVED;
            touchEvent.id_ = buttonId;
            touchEvent.x_ = touchX_[buttonId] = mouseEvent.getX();
            touchEvent.y_ = touchY_[buttonId] = mouseEvent.getY();
            touchEventsBuffer_.add(touchEvent);
            isTouched_[buttonId] = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

}
