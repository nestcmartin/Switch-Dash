package es.ucm.vdm.engine.android;

import android.view.View;

import java.util.List;

import es.ucm.vdm.engine.Input;

/**
 * Interfaz del gestor de eventos de interacción con la pantalla de Android.
 */
public interface AndroidTouchHandler extends View.OnTouchListener {

    /**
     * Comprueba si se está produciendo una interacción con la pantalla en el último frame.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return true si se está produciendo la interacción, false en caso contrario.
     */
    public boolean isTouchDown(int pointer);

    /**
     * Devuelve la coordenada x en la que se produjo la última interacción con la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return la coordenada x requerida si la pantalla fue interaccioanda
     *         en el último frame, o -1 en caso contrario.
     */
    public int getTouchX(int pointer);

    /**
     * Devuelve la coordenada y en la que se produjo la última interacción con la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return la coordenada y requerida si la pantalla fue interaccioanda
     *         en el último frame, o -1 en caso contrario.
     */
    public int getTouchY(int pointer);

    /**
     * @return la lista de todos los eventos de interacción con la pantalla en el último frame.
     */
    public List<Input.TouchEvent> getTouchEvents();

}
