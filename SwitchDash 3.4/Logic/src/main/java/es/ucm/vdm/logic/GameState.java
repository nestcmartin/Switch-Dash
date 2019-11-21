package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utils.AudioManager;

public class GameState extends State {

    public final int GAME_WIDTH = 1080;
    public final int GAME_HEIGHT = 1920;
    public boolean SOUND = true;

    protected List<GameObject> gameObjects_ = new ArrayList<>();

    public GameState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {

        for (GameObject go: gameObjects_) {
            go.update(deltaTime);
        }
    }

    @Override
    public void render(double deltaTime) {
        for (GameObject go: gameObjects_) {
            go.render(deltaTime);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}

    public void switchSound(boolean sound) {
        SOUND = sound;

        if (!SOUND) {
            AudioManager.getInstance().getSound(Assets.sounds[Assets.SoundName.MENU_MUSIC.ordinal()]).setVolume(0.0f);
            AudioManager.getInstance().getSound(Assets.sounds[Assets.SoundName.GAME_MUSIC.ordinal()]).setVolume(0.0f);
        } else {
            AudioManager.getInstance().getSound(Assets.sounds[Assets.SoundName.MENU_MUSIC.ordinal()]).setVolume(1.0f);
            AudioManager.getInstance().getSound(Assets.sounds[Assets.SoundName.GAME_MUSIC.ordinal()]).setVolume(1.0f);
        }
    }
}
