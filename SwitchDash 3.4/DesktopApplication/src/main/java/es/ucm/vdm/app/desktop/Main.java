package es.ucm.vdm.app.desktop;

import es.ucm.vdm.engine.desktop.DesktopGame;

public class Main {

    public static void main(String[] args) {

        System.out.println(("Starting DesktopGame"));

        DesktopGame game = new DesktopGame();
        game.startGame("SwitchDash", 1280, 720);
    }
}
