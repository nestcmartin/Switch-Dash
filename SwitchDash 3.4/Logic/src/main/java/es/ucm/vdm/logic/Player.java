package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utilities.Sprite;

public class Player extends GameObject {

    boolean isWhite_ = true;

    public Player(Game g, Sprite s, int x, int y, int w, int h) {
        super(g, s, x, y, w, h);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public boolean handleTouchEvent(Input.TouchEvent event){
        if(event.type_ == Input.EventType.PRESSED) {
            switchColor();
            return true;
        }
        return false;
    }

    private void switchColor(){
        isWhite_ = !isWhite_;
        System.out.println(isWhite_);

        int color = isWhite_ ? 0 : 1;
        sprite_.setFrameRow(color);
    }
}
