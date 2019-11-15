package es.ucm.vdm.logic;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Sprite;

public class MenuState extends State {

    public final int GAME_WIDTH = 1080;
    public final int GAME_HEIGHT = 1920;

    private Background background_;
    private Arrows arrows_;
    private GameObject gameLogo_;


    public MenuState(Game game) {
        super(game);

        ScaledGraphics g = game_.getGraphics();
        g.setCanvasLogicSize(GAME_WIDTH, GAME_HEIGHT);

        background_ = new Background(game_);
        background_.updateColor();

        arrows_ = new Arrows(game_);

        // Logo
        Sprite logoSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.SWITCH_DASH_LOGO.ordinal()]), 1, 1);
        int logoX = (GAME_WIDTH - logoSprite.getImage().getWidth()) / 2;
        gameLogo_ = new GameObject(game_, logoSprite, logoX, 500, logoSprite.getImage().getWidth(), logoSprite.getImage().getHeight());
    }

    @Override
    public void update(double deltaTime) {
        handleInput();

        background_.update(deltaTime);
        arrows_.update(deltaTime);
    }

    private void handleInput() {

        List<Input.KeyEvent> keyEvents = game_.getInput().getKeyEvents();
        List<Input.TouchEvent> touchEvents = game_.getInput().getTouchEvents();

        for (int i = 0; i < keyEvents.size(); i++) {
            Input.KeyEvent event = keyEvents.get(i);
            if (event.type_ == Input.EventType.PRESSED) {
                System.out.println("Pressed keyboard key: " + event.keyCode_);
            }
        }

        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type_ == Input.EventType.PRESSED) {
                System.out.println("Click with mouse key: " + event.id_);
            }
        }
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

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);

        background_.render(deltaTime);
        arrows_.render(deltaTime);
        gameLogo_.render(deltaTime);
    }
}
