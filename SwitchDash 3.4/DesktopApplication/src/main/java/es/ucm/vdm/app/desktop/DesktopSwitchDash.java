package es.ucm.vdm.app.desktop;

import java.io.IOException;

import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.desktop.DesktopGame;
import es.ucm.vdm.logic.DemoState;
import es.ucm.vdm.logic.LoadingState;

public class DesktopSwitchDash extends DesktopGame {

    private static final int windowWidth = 1280;
    private static final int windowHeight = 720;

    private DesktopSwitchDash(String windowName, int width, int height) {
        super(windowName, width, height);
    }

    @Override
    public State getStartState() {
        return new LoadingState(this);
    }

    public static void main(String[] args) {
        DesktopSwitchDash game = new DesktopSwitchDash("Switch Dash", windowWidth, windowHeight);
    }
}
