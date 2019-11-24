package es.ucm.vdm.engine;

import java.util.List;

/**
 * Interfaz del gestor de input del motor de tecnología.
 */
public interface Input {

    /**
     * Tipos de evento de entrada genéricos.
     * PRESSED:     tecla / botón / pantalla pulsada.
     * RELEASED:    tecla / botón / pantalla liberada.
     * MOVED:       cursor pulsado y arrastrado o deslizamiento por pantalla.
     */
    public enum EventType {
        PRESSED,
        RELEASED,
        MOVED
    }

    /**
     * Evento genérico de teclado.
     * Almacena información sobre el tipo de evento de entrada,
     * el código de la tecla y el carácter Unicode de la tecla.
     */
    public class KeyEvent {

        public EventType type_;
        public int keyCode_;
        public char keyChar_;

    }

    /**
     * Evento genérico de interacción con la pantalla.
     * Se utiliza indistintamente para eventos táctiles y de ratón.
     * Almacena información sobre el tipo de evento de entrada,
     * las coordenadas x e y de la interacción y el identificador
     * del accionador (los botones del ratón o los dedos).
     */
    public class TouchEvent {

        public EventType type_;
        public int x_;
        public int y_;
        public int id_;

    }

    /**
     * Comprueba si una tecla se ha pulsado en el último frame.
     * @param keyCode el código de la tecla que se quiere consultar.
     * @return true si se ha pulsado, false en caso contrario.
     */
    public boolean isKeyPressed(int keyCode);

    /**
     * Comprueba si se está produciendo una interacción con la pantalla en el último frame.
     * @param pointer el identificador del accionador que se quiere consultar.
     * @return true si se está produciendo la interacción, false en caso contrario.
     */
    public boolean isTouchDown(int pointer);

    /**
     * Devuelve la coordenada x en la que se produjo la última interacción con la pantalla.
     * @param pointer el identificador del accionador que se quiere consultar.
     * @return la coordenada x requerida si la pantalla fue interaccioanda
     *         en el último frame, o -1 en caso contrario.
     */
    public int getTouchX(int pointer);

    /**
     * Devuelve la coordenada y en la que se produjo la última interacción con la pantalla.
     * @param pointer el identificador del accionador que se quiere consultar.
     * @return la coordenada y requerida si la pantalla fue interaccioanda
     *         en el último frame, o -1 en caso contrario.
     */
    public int getTouchY(int pointer);

    /**
     * @return la lista de todos los eventos de teclado generados en el último frame.
     */
    public List<KeyEvent> getKeyEvents();

    /**
     * @return la lista de todos los eventos de interacción con la pantalla en el último frame.
     */
    public List<TouchEvent> getTouchEvents();

}