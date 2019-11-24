package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Sound;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utils.AudioManager;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.objects.ScreenFader;

/**
 * Implementación de State con funciones añadidas que tienen en común todos los estados.
 * Como por ejemplo listas de GameObjects o transición entre escenas con un fading a blanco.
 */
public class GameState extends State {

    protected final int GAME_WIDTH = 1080;
    protected final int GAME_HEIGHT = 1920;
    protected boolean SOUND = true;

    protected Sound menuMusic_;
    protected Sound gameMusic_;

    protected List<GameObject> gameObjects_ = new ArrayList<>();
    protected ScreenFader screenFader_;
    protected GameState newState_;
    protected boolean isSwitching = false;

    /**
     * Constructora de clase.
     * @param game referencia al juego de Game que gestiona el bucle.
     */
    public GameState(Game game) {
        super(game);

        menuMusic_ = AudioManager.getInstance().getSound(Assets.sounds[Assets.SoundName.MENU_MUSIC.ordinal()]);
        gameMusic_ = AudioManager.getInstance().getSound(Assets.sounds[Assets.SoundName.GAME_MUSIC.ordinal()]);
    }

    /**
     * Procesa el input del juego.
     * Actualiza todos los objetos de la lista de GameObjects.
     * Tras finalizar comprueba si se está cambiando entre estados y si es así ejecuta el cambio.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        if(!screenFader_.isFading())
            handleInput();

        for (GameObject go: gameObjects_) {
            go.update(deltaTime);
        }

        if(isSwitching && !screenFader_.isFading())
            game_.setState(newState_);
    }

    /**
     * Renderiza todos los objetos de la lista de GameObjects
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void render(double deltaTime) {
        for (GameObject go: gameObjects_) {
            go.render(deltaTime);
        }
        game_.getGraphics().drawBars(0x000000);
    }


    /**
     * Método a implementar por los estados que hereden de este.
     * Procesa el input del juego.
     */
    protected void handleInput() {}

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

    /**
     * Activa o desactiva los sonidos que se estén reproduciendo dependiendo del parametro sound.
     * @param sound booleano que determina si los sonidos se deben escuchar o no
     */
    public void switchSound(boolean sound) {
        SOUND = sound;
        if (!SOUND) AudioManager.getInstance().muteAllSounds();
        else AudioManager.getInstance().unmuteAllSounds();
    }

    /**
     * Crea y añade el ScreenFader a la lista de GameObjects.
     * Debe ser llamado al final de la constructora de todos los estados hereden de esta clase.
     */
    protected void addScreenFader(){
        Sprite whiteSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.WHITE.ordinal()]), 1, 1);
        screenFader_ = new ScreenFader(game_, whiteSprite, 0.2f);
        gameObjects_.add(screenFader_);
    }

    /**
     * Realiza un fade a blanco en la pantalla y cambia el estado actual del juego
     * una vez finalizado el fade.
     * @param state el nuevo estado del juego.
     */
    protected void switchStateWithFading(GameState state){
        if(!isSwitching){
            isSwitching = true;
            screenFader_.startFadeIn(false);
            newState_ = state;
        }
    }
}
