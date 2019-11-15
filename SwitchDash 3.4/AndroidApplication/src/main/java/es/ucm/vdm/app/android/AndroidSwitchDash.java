package es.ucm.vdm.app.android;

import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.android.AndroidGame;
import es.ucm.vdm.logic.states.LoadingState;

public class AndroidSwitchDash extends AndroidGame {

    @Override
    public State getStartState() {
        return new LoadingState(this);
    }
}
