package es.ucm.vdm.engine.android;

import android.view.View;

import java.util.List;

import es.ucm.vdm.engine.Input;

/**
 * Implementación para Android del gestor de input del motor de tecnología.
 */
public class AndroidInput implements Input {

    private AndroidKeyboardHandler keyHandler_;
    private AndroidTouchHandler touchHandler_;

    /**
     * Constructora de clase.
     * @param view la ventana de Android.
     * @param scaleX el factor de escala x de la pantalla del dispositivo Android.
     * @param scaleY el factor de escala y de la pantalla del dispositivo Android.
     */
    public AndroidInput(View view, float scaleX, float scaleY) {
        keyHandler_ = new AndroidKeyboardHandler(view);
        touchHandler_ = new AndroidMultiTouchHandler(view, scaleX, scaleY);
    }

    /**
     * Consulta si se ha pulsado una tecla.
     * @param keyCode el código de la tecla que se quiere consultar.
     * @return true si se ha pulsado la tecla en el último frame, false en caso contrario.
     */
    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler_.isKeyPressed(keyCode);
    }

    /**
     * Comprueba si el dedo indicado está tocando la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return true si el dedo indicado está tocando la pantalla, false en caso contrario.
     */
    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler_.isTouchDown(pointer);
    }

    /**
     * Devuelve la coordenada x en la que el dedo indicado tocó por última vez la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return la coordenada x requerida si la pantalla fue tocada
     *         en el último frame, o -1 en caso contrario.
     */
    @Override
    public int getTouchX(int pointer) {
        return touchHandler_.getTouchX(pointer);
    }

    /**
     * Devuelve la coordenada y en la que el dedo indicado tocó por última vez la pantalla.
     * @param pointer el identificador del dedo que se quiere consultar.
     * @return la coordenada y requerida si la pantalla fue tocada
     *         en el último frame, o -1 en caso contrario.
     */
    @Override
    public int getTouchY(int pointer) {
        return touchHandler_.getTouchY(pointer);
    }

    /**
     * @return la lista de todos los eventos de teclado generados en el último frame.
     */
    public List<KeyEvent> getKeyEvents() {
        return keyHandler_.getKeyEvents();
    }

    /**
     * @return la lista de todos los eventos de interacción con la pantalla en el último frame.
     */
    public List<TouchEvent> getTouchEvents() {
        return touchHandler_.getTouchEvents();
    }
}