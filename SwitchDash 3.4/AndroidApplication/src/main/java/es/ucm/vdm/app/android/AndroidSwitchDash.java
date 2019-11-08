package es.ucm.vdm.app.android;

import android.os.Bundle;

import es.ucm.vdm.engine.android.AndroidGame;

public class AndroidSwitchDash extends AndroidGame {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
