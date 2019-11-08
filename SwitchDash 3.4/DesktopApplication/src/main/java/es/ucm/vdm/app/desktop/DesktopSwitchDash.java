package es.ucm.vdm.app.desktop;

import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.desktop.DesktopGame;
import es.ucm.vdm.logic.DemoState;

public class DesktopSwitchDash extends DesktopGame {

    private static final int windowWidth = 1280;
    private static final int windowHeight = 720;

    private DesktopSwitchDash(String windowName, int width, int height) {
        super(windowName, width, height);
    }

    @Override
    public State getStartState() {
        return new DemoState(this);
    }

    public static void main(String[] args) {
        DesktopSwitchDash game = new DesktopSwitchDash("Switch Dash", windowWidth, windowHeight);
    }
}
