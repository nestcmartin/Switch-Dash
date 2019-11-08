package es.ucm.vdm.app.android;

import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.android.AndroidGame;
import es.ucm.vdm.logic.DemoState;

public class AndroidSwitchDash extends AndroidGame {

    @Override
    public State getStartState() {
        return new DemoState(this);
    }
}
