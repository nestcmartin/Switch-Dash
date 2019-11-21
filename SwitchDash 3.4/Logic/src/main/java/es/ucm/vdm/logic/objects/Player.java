package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

public class Player extends GameObject {

    boolean isAlive_ = true;
    boolean isWhite_ = true;

    public Player(Game g, Sprite s, int x, int y, int w, int h) {
        super(g, s, x, y, w, h);
    }

    public int getColor(){ return isWhite_? 0 : 1; }
    public void die(){ isAlive_ = false; }

    public boolean handleKeyEvent(Input.KeyEvent event){
        if(event.type_ == Input.EventType.PRESSED) {
            if (event.keyChar_ == ' ')
            {
                switchColor();
                return true;
            }
        }
        return false;
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

        int color = isWhite_ ? 0 : 1;
        sprite_.setFrameRow(color);
    }

    @Override
    public void render(double deltaTime) {
        if(isAlive_)
            super.render(deltaTime);
    }
}
