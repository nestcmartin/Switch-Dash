package es.ucm.vdm.logic.states;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.objects.Arrows;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.objects.Background;
import es.ucm.vdm.logic.objects.Button;
import es.ucm.vdm.logic.FontMapper;
import es.ucm.vdm.logic.GameObject;
import es.ucm.vdm.logic.GameState;
import es.ucm.vdm.logic.objects.PulsatingSprite;

public class GameOverState extends GameState {

    private Button howToPlayButton_;
    private Button soundButton_;

    public GameOverState(Game game, int score) {
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

        // GameOver
        Sprite gameOver = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.GAME_OVER.ordinal()]), 1, 1);
        int gameOverX = (GAME_WIDTH - gameOver.getWidth()) / 2;
        gameObjects_.add(new GameObject(game_, gameOver, gameOverX, 356, gameOver.getWidth(), gameOver.getHeight()));

        // Score
        if (score < 10) {
            Sprite sUnidades = FontMapper.getInstance().getSprite(String.valueOf(score % 10));
            int uniX = (GAME_WIDTH - sUnidades.getWidth()) / 2;
            gameObjects_.add(new GameObject(game_, sUnidades, uniX, 800, sUnidades.getWidth(), sUnidades.getHeight()));
        }
        else if (score >= 10 && score < 100) {
            Sprite sDecenas = FontMapper.getInstance().getSprite(String.valueOf((score % 100) / 10));
            int decX = (GAME_WIDTH - (2 * sDecenas.getWidth())) / 2;
            gameObjects_.add(new GameObject(game_, sDecenas, decX, 800, sDecenas.getWidth(), sDecenas.getHeight()));

            Sprite sUnidades = FontMapper.getInstance().getSprite(String.valueOf(score % 10));
            int uniX = decX + sDecenas.getWidth();
            gameObjects_.add(new GameObject(game_, sUnidades, uniX, 800, sUnidades.getWidth(), sUnidades.getHeight()));
        }
        else {
            Sprite sCentenas = FontMapper.getInstance().getSprite(String.valueOf(score / 100));
            int centX = (GAME_WIDTH - (3 * sCentenas.getWidth())) / 2;
            gameObjects_.add(new GameObject(game_, sCentenas, centX, 800, sCentenas.getWidth(), sCentenas.getHeight()));

            Sprite sDecenas = FontMapper.getInstance().getSprite(String.valueOf((score % 100) / 10));
            int decX = centX + sCentenas.getWidth();
            gameObjects_.add(new GameObject(game_, sDecenas, decX, 800, sDecenas.getWidth(), sDecenas.getHeight()));

            Sprite sUnidades = FontMapper.getInstance().getSprite(String.valueOf(score % 10));
            int uniX = decX + sDecenas.getWidth();
            gameObjects_.add(new GameObject(game_, sUnidades, uniX, 800, sUnidades.getWidth(), sUnidades.getHeight()));
        }

        // PlayAgain
        Sprite playAgain = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.PLAY_AGAIN.ordinal()]), 1, 1);
        int playAgainX = (GAME_WIDTH - playAgain.getImage().getWidth()) / 2;
        gameObjects_.add(new PulsatingSprite(game_, playAgain, playAgainX, 1464, playAgain.getImage().getWidth(), playAgain.getImage().getHeight(), 1.2f));

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
        screenFader_.startFadeIn(true);
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
                if (event.keyChar_ == ' ') switchStateWithFading(new MainGameState(game_));
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

}
