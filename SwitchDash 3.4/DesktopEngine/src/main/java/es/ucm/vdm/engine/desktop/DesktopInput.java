package es.ucm.vdm.engine.desktop;

import java.util.List;

import javax.swing.JFrame;

import es.ucm.vdm.engine.Input;

/**
 * Implementación para Desktop del gestor de input del motor de tecnología.
 */
public class DesktopInput implements Input {

    private DesktopKeyboardHandler keyHandler_;
    private DesktopMouseHandler mouseHandler_;

    /**
     * Constructora de clase.
     * @param window la ventana de JFrame.
     */
    public DesktopInput(JFrame window) {

        keyHandler_ = new DesktopKeyboardHandler(window);
        mouseHandler_ = new DesktopMouseHandler(window);
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
     * Comprueba si el botón del ratón indicado está pulsado.
     * @param id el identificador del botón del ratón que se quiere consultar.
     * @return true si el botón del ratón indicado está pulsado, false en caso contrario.
     */
    @Override
    public boolean isTouchDown(int id) {
        return mouseHandler_.isTouchDown(id);
    }

    /**
     * Devuelve la coordenada x en la que el botón del ratón indicado hizo click por última vez.
     * @param id el identificador del botón del ratón que se quiere consultar.
     * @return la coordenada x requerida si el botón indicado fue pulsado
     *         en el último frame, o -1 en caso contrario.
     */
    @Override
    public int getTouchX(int id) {
        return mouseHandler_.getTouchX(id);
    }

    /**
     * Devuelve la coordenada y en la que el botón del ratón indicado hizo click por última vez.
     * @param id el identificador del botón del ratón que se quiere consultar.
     * @return la coordenada y requerida si el botón indicado fue pulsado
     *         en el último frame, o -1 en caso contrario.
     */
    @Override
    public int getTouchY(int id) {
        return mouseHandler_.getTouchY(id);
    }

    /**
     * @return la lista de todos los eventos de teclado generados en el último frame.
     */
    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler_.getKeyEvents();
    }

    /**
     * @return la lista de todos los eventos de ratón en el último frame.
     */
    @Override
    public List<TouchEvent> getTouchEvents() { return  mouseHandler_.getTouchEvents(); }
}
