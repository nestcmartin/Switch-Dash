package es.ucm.vdm.engine;

import java.util.List;

public interface Input {

    public class KeyEvent {

        public static final int KEY_DOWN = 0;
        public static final int KEY_UP = 1;

        public int type_;
        public int keyCode_;
        public char keyChar_;

    }

    public static class TouchEvent {

        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;

        public int type_;
        public int x_;
        public int y_;
        public int pointer_;

    }

    public boolean isKeyPressed(int keyCode);
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);
    public int getTouchY(int pointer);

    public List<KeyEvent> getKeyEvents();
    public List<TouchEvent> getTouchEvents();

}