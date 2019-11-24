package es.ucm.vdm.engine.android;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;

/**
 * Implementación single-touch del gestor de eventos de interacción con la pantalla de Android.
 */
public class AndroidSingleTouchHandler implements AndroidTouchHandler {

    private float scaleX_;
    private float scaleY_;

    private boolean isTouched_;
    private int touchX_;
    private int touchY_;

    private Pool<Input.TouchEvent> touchEventPool_;
    private List<Input.TouchEvent> touchEvents_ = new ArrayList<Input.TouchEvent>();
    private List<Input.TouchEvent> touchEventsBuffer_ = new ArrayList<Input.TouchEvent>();


    /**
     * Constructora de clase.
     * Se tiene en cuenta la densidad de píxeles del dispositivo Android para
     * gestionar adecuadamente las posiciones de interacción con la pantalla.
     * @param view la ventana de Android.
     * @param scaleX el factor de escala x de la pantalla del dispositivo Android.
     * @param scaleY el factor de escala y de la pantalla del dispositivo Android.
     */
    public AndroidSingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        touchEventPool_ = new Pool<Input.TouchEvent>(factory, 100);
        view.setOnTouchListener(this);
        this.scaleX_ = scaleX;
        this.scaleY_ = scaleY;
    }

    /**
     * Registra un evento de interacción con la pantalla del dispositivo Android.
     * El evento de Android se traduce al evento genérico del gestor de input del motor.
     * Se registran los parámetros del evento para su consulta directa y
     * se almacena el evento en la lista de eventos.
     * @param v la ventana de Android.
     * @param event el evento táctil de Android.
     * @return true si se registra un evento, false en caso contrario.
     */
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            Input.TouchEvent touchEvent = touchEventPool_.newObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type_ = Input.EventType.PRESSED;
                    isTouched_ = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type_ = Input.EventType.MOVED;
                    isTouched_ = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type_ = Input.EventType.RELEASED;
                    isTouched_ = false;
                    break;
            }
            touchEvent.id_ = 0;
            touchEvent.x_ = touchX_ = (int)(event.getX() * scaleX_);
            touchEvent.y_ = touchY_ = (int)(event.getY() * scaleY_);
            touchEventsBuffer_.add(touchEvent);
            return true;
        }
    }

    /**
     * Consulta si se está tocando la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return true si un dedo está tocando la pantalla, false en caso contario.
     */
    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if(pointer == 0) return isTouched_;
            else return false;
        }
    }

    /**
     * Devuelve la coordenada x en la que se tocó por última vez la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return la coordenada x requerida si la pantalla fue tocada
     *         en el último frame, o -1 en caso contrario.
     */
    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            return touchX_;
        }
    }

    /**
     * Devuelve la coordenada y en la que se tocó por última vez la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return la coordenada y requerida si la pantalla fue tocada
     *         en el último frame, o -1 en caso contrario.
     */
    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            return touchY_;
        }
    }

    /**
     * Devuelve todos los eventos de interacción con la pantalla registrados en el frame actual.
     * Libera todos los eventos de interacción con la pantalla registrados en el frame anterior.
     * @return la lista de todos los eventos de interacción con la pantalla en el último frame.
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
}