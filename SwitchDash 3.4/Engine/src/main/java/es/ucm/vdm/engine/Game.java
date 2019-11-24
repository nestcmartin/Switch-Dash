package es.ucm.vdm.engine;

/**
 * Interfaz de juego básico.
 */
public interface Game {

    /**
     * @return el gerstor de input del motor de tecnología.
     */
    public Input getInput();

    /**
     * @return el gestor de audio del motor de tecnología.
     */
    public Audio getAudio();

    /**
     * @return el gestor gráfico del motor de tecnología.
     */
    public ScaledGraphics getGraphics();

    /**
     * Cambia el estado actual del juego.
     * @param state el nuevo estado del juego.
     */
    public void setState(State state);

    /**
     * @return el estado actual del juego.
     */
    public State getCurrentState();

    /**
     * @return el estado inicial del juego.
     */
    public State getStartState();

}
