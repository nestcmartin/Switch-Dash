package es.ucm.vdm.logic;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Random;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class MainState extends State {

    private Sprite background_;
    private int currentBackgroundColor_ = 0;
    private int[] backgroundColors_ = new int[] {0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0, 0x553982, 0x28324e, 0xf37934, 0xd14b41, 0x75706b};

    public MainState(Game game) {
        super(game);

        currentBackgroundColor_ = Random.randomInt(0, backgroundColors_.length - 1);
        Pixmap p = PixmapManager.getInstance().getPixmap(Assets.imageFiles[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]);
        Rect src = new Rect(0, 0, 676, 3070);
        background_ = new Sprite(p, src, 202, 0, 676, 3070);
    }

    @Override
    public void update(double deltaTime) {
        handleInput();
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
        Rect dst = new Rect(0, 0, 1080, 2220);
        g.fillRect(dst, backgroundColors_[currentBackgroundColor_]);
        background_.draw(g);

    }
}
