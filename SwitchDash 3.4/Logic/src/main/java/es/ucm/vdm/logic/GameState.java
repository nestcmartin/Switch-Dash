package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utils.AudioManager;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.objects.ScreenFader;

public class GameState extends State {

    public final int GAME_WIDTH = 1080;
    public final int GAME_HEIGHT = 1920;
    public boolean SOUND = true;

    protected List<GameObject> gameObjects_ = new ArrayList<>();
    protected ScreenFader screenFader_;
    protected GameState newState_;
    protected boolean isSwitching = false;

    public GameState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject go: gameObjects_) {
            go.update(deltaTime);
        }

        if(isSwitching && !screenFader_.isFading())
            game_.setState(newState_);
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
            AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.MENU_MUSIC.ordinal()]).setVolume(0.0f);
            AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.GAME_MUSIC.ordinal()]).setVolume(0.0f);
        } else {
            AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.MENU_MUSIC.ordinal()]).setVolume(1.0f);
            AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.GAME_MUSIC.ordinal()]).setVolume(1.0f);
        }
    }

    protected void addScreenFader(){
        Sprite whiteSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.WHITE.ordinal()]), 1, 1);
        screenFader_ = new ScreenFader(game_, whiteSprite, 0.2f);
        gameObjects_.add(screenFader_);
    }

    protected void switchState(GameState state){
        if(!isSwitching){
            screenFader_.startFadeIn(false);
            isSwitching = true;
            newState_ = state;
        }
    }
}
