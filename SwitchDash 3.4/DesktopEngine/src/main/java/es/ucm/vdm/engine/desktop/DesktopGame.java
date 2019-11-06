package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.State;

public class DesktopGame implements Game {

    private DesktopWindow window_;
    private Graphics graphics_;
    private Input input_;
    private State state_;

    public void startGame(String windowName, int width, int height){
        state_ = getStartState();
        window_ = new DesktopWindow(this, windowName, width, height);
        window_.run();
    }

    public Input getInput() {
        return input_;
    }

    public Graphics getGraphics() {
        return graphics_;
    }

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

    public State getCurrentState() {
        return state_;
    }

    public State getStartState() {
        return null;
    }
}
