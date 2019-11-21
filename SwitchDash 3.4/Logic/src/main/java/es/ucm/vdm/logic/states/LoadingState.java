package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utils.AudioManager;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.logic.Assets;

public class LoadingState extends State {

    public LoadingState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {
        ScaledGraphics g = game_.getGraphics();
        Audio a = game_.getAudio();

        for (int i = 0; i < Assets.images.length; i++) {
            PixmapManager.getInstance().addPixmap(Assets.images[i], g.newPixmap(Assets.images[i]));
        }

        for (int i = 0; i < Assets.musics.length; i++) {
            AudioManager.getInstance().addMusic(Assets.musics[i], a.newMusic(Assets.musics[i]));
        }

        for (int i = 0; i < Assets.sounds.length; i++) {
            AudioManager.getInstance().addSound(Assets.sounds[i], a.newSound(Assets.sounds[i]));
        }

        AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.MENU_MUSIC.ordinal()]).play();
        game_.setState(new MenuState(game_, true));
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
