package es.ucm.vdm.logic.states;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utils.AudioManager;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.objects.Arrows;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.objects.Background;
import es.ucm.vdm.logic.objects.Button;
import es.ucm.vdm.logic.GameObject;
import es.ucm.vdm.logic.GameState;
import es.ucm.vdm.logic.objects.PulsatingSprite;

public class MenuState extends GameState {

    private Button howToPlayButton_;
    private Button soundButton_;

    public MenuState(Game game, boolean fadeIn) {
        super(game);
        ScaledGraphics g = game_.getGraphics();
        g.setCanvasLogicSize(GAME_WIDTH, GAME_HEIGHT);
        g.scaleCanvas();

        // Background
        Background background_ = new Background(game_);
        background_.updateColor();
        gameObjects_.add(background_);

        // Moving Arrows
        gameObjects_.add(new Arrows(game_));

        // Logo
        Sprite logoSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.SWITCH_DASH_LOGO.ordinal()]), 1, 1);
        int logoX = (GAME_WIDTH - logoSprite.getWidth()) / 2;
        gameObjects_.add(new GameObject(game_, logoSprite, logoX, 356, logoSprite.getWidth(), logoSprite.getHeight()));

        // TapToPlay
        Sprite tapSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.TAP_TO_PLAY.ordinal()]), 1, 1);
        int tapX = (GAME_WIDTH - tapSprite.getWidth()) / 2;
        gameObjects_.add(new PulsatingSprite(game_, tapSprite, tapX, 950, tapSprite.getWidth(), tapSprite.getHeight(), 1.2f));

        // HowToPlayButton
        Sprite htpSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.BUTTONS.ordinal()]), 1, 10);
        int htpX = (GAME_WIDTH - htpSprite.getWidth()) - 30;
        howToPlayButton_ = new Button(game_, htpSprite , htpX, 90, htpSprite.getWidth(), htpSprite.getHeight());
        gameObjects_.add(howToPlayButton_);

        // SoundButton
        Sprite soundSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.BUTTONS.ordinal()]), 1, 10);
        soundButton_ = new Button(game_, soundSprite , 30, 90, soundSprite.getWidth(), soundSprite.getHeight());
        soundButton_.updateSpriteFrame(0, 2);
        gameObjects_.add(soundButton_);

        // Screen Fader
        addScreenFader();
        if(fadeIn)
            screenFader_.startFadeIn(true);
    }

    @Override
    public void update(double deltaTime) {
        if(!screenFader_.isFading())
            handleInput();
        super.update(deltaTime);
    }

    private void handleInput() {

        List<Input.KeyEvent> keyEvents = game_.getInput().getKeyEvents();
        List<Input.TouchEvent> touchEvents = game_.getInput().getTouchEvents();

        for (int i = 0; i < keyEvents.size(); i++) {
            Input.KeyEvent event = keyEvents.get(i);
            if (event.type_ == Input.EventType.RELEASED) {
                if (event.keyChar_ == ' ') switchStateWithFading(new HowToPlayState(game_));
            }
        }

        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (howToPlayButton_.handleTouchEvent(event))
                switchStateWithFading(new HowToPlayState(game_));
            else if (soundButton_.handleTouchEvent(event)) {
                switchSound(!SOUND);
                if (SOUND) soundButton_.updateSpriteFrame(0, 2);
                else soundButton_.updateSpriteFrame(0, 3);
            }
            else if (event.type_ == Input.EventType.RELEASED)
                switchStateWithFading(new MainGameState(game_));
        }
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);
        super.render(deltaTime);
    }

    @Override
    public void resume() {
        super.resume();
        if (!menuMusic_.isPlaying()) menuMusic_.play(true);
    }
}
