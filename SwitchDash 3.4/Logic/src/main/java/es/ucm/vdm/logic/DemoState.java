package es.ucm.vdm.logic;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.State;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Rect;

public class DemoState extends State {

    public DemoState(Game game) {
        super(game);
    }

    @Override
    public void update(double deltaTime) {

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
        g.clear(0xffff00ff);

        Pixmap p = PixmapManager.getInstance().getPixmap(Assets.imageFiles[Assets.ImageName.ARROWS_BACKGROUND.ordinal()]);
        Rect src = new Rect(0, 0, 676, 3070);
        Rect dst = new Rect(0, 0, 1080, 2220);
        game_.getGraphics().drawPixmap(p, src, dst);
    }
}
