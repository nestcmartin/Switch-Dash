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

public class HowToPlayState extends GameState {

    private Button goToMenuButton_;
    private Button soundButton_;

    public HowToPlayState(Game game) {
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

        // HowToPlay
        Sprite howToPlaySprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.HOW_TO_PLAY.ordinal()]), 1, 1);
        int howToPlayX = (GAME_WIDTH - howToPlaySprite.getImage().getWidth()) / 2;
        gameObjects_.add(new GameObject(game_, howToPlaySprite, howToPlayX, 290, howToPlaySprite.getImage().getWidth(), howToPlaySprite.getImage().getHeight()));

        // Instructions
        Sprite instructionsSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.INSTRUCTIONS.ordinal()]), 1, 1);
        int instructionsX = (GAME_WIDTH - instructionsSprite.getImage().getWidth()) / 2;
        gameObjects_.add(new GameObject(game_, instructionsSprite, instructionsX, 768, instructionsSprite.getImage().getWidth(), instructionsSprite.getImage().getHeight()));

        // TapToPlay
        Sprite tapSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.TAP_TO_PLAY.ordinal()]), 1, 1);
        int tapX = (GAME_WIDTH - tapSprite.getImage().getWidth()) / 2;
        gameObjects_.add(new PulsatingSprite(game_, tapSprite, tapX, 1464, tapSprite.getImage().getWidth(), tapSprite.getImage().getHeight(), 1.2f));

        // GoToMenuButton
        Sprite gtmSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.BUTTONS.ordinal()]), 1, 10);
        gtmSprite.setFrameCol(1);
        int gtmX = (GAME_WIDTH - gtmSprite.getWidth()) - 30;
        goToMenuButton_ = new Button(game_, gtmSprite , gtmX, 90, gtmSprite.getWidth(), gtmSprite.getHeight());
        gameObjects_.add(goToMenuButton_);

        // SoundButton
        Sprite soundSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.BUTTONS.ordinal()]), 1, 10);
        soundButton_ = new Button(game_, soundSprite , 30, 90, soundSprite.getWidth(), soundSprite.getHeight());
        soundButton_.updateSpriteFrame(0, 2);
        gameObjects_.add(soundButton_);
    }

    @Override
    public void update(double deltaTime) {
        handleInput();
        super.update(deltaTime);
    }

    private void handleInput() {

        List<Input.KeyEvent> keyEvents = game_.getInput().getKeyEvents();
        List<Input.TouchEvent> touchEvents = game_.getInput().getTouchEvents();

        for (int i = 0; i < keyEvents.size(); i++) {
            Input.KeyEvent event = keyEvents.get(i);
            if (event.type_ == Input.EventType.RELEASED) {
                if (event.keyChar_ == ' ') game_.setState(new MainGameState(game_));
            }
        }

        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(goToMenuButton_.handleTouchEvent(event))
                game_.setState(new MenuState(game_));
            else if (soundButton_.handleTouchEvent(event)) {
                switchSound(!SOUND);
                if (SOUND) soundButton_.updateSpriteFrame(0, 2);
                else soundButton_.updateSpriteFrame(0, 3);
            }
            else if (event.type_ == Input.EventType.RELEASED)
                game_.setState(new MainGameState(game_));
        }
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);
        super.render(deltaTime);
    }
}
