package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.Audio;
import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utils.AudioManager;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.logic.Assets;

/**
 * Estado inicial del juego.
 * Es el estado que se encarga de cargar todos los recursos necesarios antes de pasar a MenuState.
 */
public class LoadingState extends State {

    /**
     * Constructora de clase.
     * @param game referencia al juego de Game que gestiona el bucle.
     */
    public LoadingState(Game game) {
        super(game);
    }

    /**
     * En este caso este método solo se ejecuta una vez para carga los recursos y cuando finaliza
     * se procede al cambio de estado a MenuState.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        ScaledGraphics g = game_.getGraphics();
        Audio a = game_.getAudio();

        for (int i = 0; i < Assets.images.length; i++) {
            PixmapManager.getInstance().addPixmap(Assets.images[i], g.newPixmap(Assets.images[i]));
        }

        for (int i = 0; i < Assets.sounds.length; i++) {
            AudioManager.getInstance().addSound(Assets.sounds[i], a.newSound(Assets.sounds[i]));
        }

        game_.setState(new MenuState(game_, false));
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
