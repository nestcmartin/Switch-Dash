package es.ucm.vdm.engine;

/**
 * Interfaz de los estados de juego.
 */
public abstract class State {

    protected final Game game_;

    /**
     * Constructora de clase.
     * @param game referencia al juego al que pertenece el estado.
     */
    public State(Game game) {
        this.game_ = game;
    }

    /**
     * Actualiza la lógica del juego.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    public abstract void update(double deltaTime);

    /**
     * Pinta el estado del juego.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    public abstract void render(double deltaTime);

    /**
     * Método que se llama cuando el bucle principal de juego queda suspendido.
     */
    public abstract void pause();

    /**
     * Método que se llama cuando el bucle principal de juego se reanuda.
     */
    public abstract void resume();

    /**
     * Método que se llama cuando el estado va a ser eliminado.
     */
    public abstract void dispose();

}
