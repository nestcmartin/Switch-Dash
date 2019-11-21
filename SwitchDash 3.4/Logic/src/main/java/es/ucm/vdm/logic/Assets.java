package es.ucm.vdm.logic;

public class Assets {

    public enum ImageName {
        ARROWS_BACKGROUND,
        BACKGROUNDS,
        BALLS,
        BUTTONS,
        GAME_OVER,
        HOW_TO_PLAY,
        INSTRUCTIONS,
        PLAY_AGAIN,
        PLAYERS,
        SCORE_FONT,
        SWITCH_DASH_LOGO,
        TAP_TO_PLAY,
        WHITE
    };

    public static final String[] images = {
        "arrowsBackground.png",
        "backgrounds.png",
        "balls.png",
        "buttons.png",
        "gameOver.png",
        "howToPlay.png",
        "instructions.png",
        "playAgain.png",
        "players.png",
        "scoreFont.png",
        "switchDashLogo.png",
        "tapToPlay.png",
        "white.png"
    };

    public enum SoundName {
        MENU_MUSIC,
        GAME_MUSIC,
        POINT_SOUND
    };

    public static final String[] sounds = {
            "menu.wav",
            "game.wav",
            "point.wav"
    };


}
