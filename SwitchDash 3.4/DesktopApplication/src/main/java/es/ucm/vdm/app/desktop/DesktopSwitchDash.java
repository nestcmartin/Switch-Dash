package es.ucm.vdm.app.desktop;

import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.desktop.DesktopGame;
import es.ucm.vdm.logic.DemoState;

public class DesktopSwitchDash extends DesktopGame {

    public DesktopSwitchDash(String windowName, int width, int height) {
        super(windowName, width, height);
    }

    @Override
    public State getStartState() {
        return new DemoState(this);
    }
}
