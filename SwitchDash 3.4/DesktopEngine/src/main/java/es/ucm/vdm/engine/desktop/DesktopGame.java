package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.State;

/**
 * Implementación para Desktop del juego.
 */
public class DesktopGame implements Game {

    public static final String assetsPath_ = "assets/";

    private DesktopRenderView window_;
    private DesktopGraphics graphics_;
    private DesktopAudio audio_;
    private Input input_;
    private State state_;

    /**
     * Constructora de clase.
     * Se inicializan todos los sitemas del motor.
     * @param windowName nombre de la ventana.
     * @param width ancho de la ventana.
     * @param height alto de la ventana.
     */
    public DesktopGame(String windowName, int width, int height) {
        window_ = new DesktopRenderView(this, windowName, width, height);
        window_.setIgnoreRepaint(true);
        window_.setVisible(true);

        graphics_ = new DesktopGraphics(window_);
        audio_ = new DesktopAudio();
        input_ = new DesktopInput(window_);
        state_ = getStartState();

        window_.run();
    }

    /**
     * @return el gestor de input del juego.
     */
    @Override
    public Input getInput() {
        return input_;
    }

    /**
     * @return el gestor de audio del juego.
     */
    @Override
    public Audio getAudio() {
        return audio_;
    }

    /**
     * @return el gestor gráfico del juego.
     */
    @Override
    public DesktopGraphics getGraphics() {
        return graphics_;
    }

    /**
     * Gestiona el cambio de un estado a otro.
     * Pausa el estado actual y lo libera, para luego
     * inicializar el nuevo estado y establecerlo como el actual.
     * @param state el nuevo estado del juego.
     */
    @Override
    public void setState(State state) {
        if (state == null) {
            throw new IllegalArgumentException("State must not be null");
        }
        this.state_.pause();
        this.state_.dispose();
        state.resume();
        state.update(0);
        this.state_ = state;
    }

    /**
     * @return el estado actual del juego.
     */
    @Override
    public State getCurrentState() {
        return state_;
    }

    /**
     * Método a implementar por las aplicaciones finales.
     * Normalmente este estado define el punto de entrada al juego.
     * @return el estado inicial del juego.
     */
    @Override
    public State getStartState() {
        return null;
    }
}
