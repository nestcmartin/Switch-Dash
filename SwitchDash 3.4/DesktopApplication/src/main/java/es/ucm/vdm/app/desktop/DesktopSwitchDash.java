package es.ucm.vdm.app.desktop;

import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.desktop.DesktopGame;
import es.ucm.vdm.logic.DemoState;

public class DesktopSwitchDash extends DesktopGame {

    static final int windowWidth = 1280;
    static final int windowHeight = 720;

    public DesktopSwitchDash(String windowName, int width, int height) {
        super(windowName, width, height);
    }

    @Override
    public State getStartState() {
        return new DemoState(this);
    }

    public static void main(String[] args) {
        DesktopSwitchDash game = new DesktopSwitchDash("SwitchDash", windowWidth, windowHeight);
    }
}
