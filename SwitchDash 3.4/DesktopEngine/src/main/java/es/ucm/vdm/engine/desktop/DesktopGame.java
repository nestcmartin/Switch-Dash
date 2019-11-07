package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.State;

public class DesktopGame implements Game {

    private DesktopWindow window_;
    private DesktopGraphics graphics_;
    private Input input_;
    private State state_;

    public DesktopGame(String windowName, int width, int height){
        window_ = new DesktopWindow(this, windowName, width, height);
        graphics_ = new DesktopGraphics(window_);
        state_ = getStartState();
        window_.init();
        window_.run();
    }

    public Input getInput() {
        return input_;
    }

    public DesktopGraphics getGraphics() {
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
