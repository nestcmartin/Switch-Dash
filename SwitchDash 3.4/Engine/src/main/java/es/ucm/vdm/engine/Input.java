package es.ucm.vdm.engine;

import java.util.List;

public interface Input {

    public enum EventType {
        PRESSED,
        RELEASED,
        MOVED
    }

    public class KeyEvent {

        public EventType type_;
        public int keyCode_;
        public char keyChar_;

    }

    public static class TouchEvent {

        public EventType type_;
        public int x_;
        public int y_;
        public int id_;

    }

    public boolean isKeyPressed(int keyCode);
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);
    public int getTouchY(int pointer);

    public List<KeyEvent> getKeyEvents();
    public List<TouchEvent> getTouchEvents();

}