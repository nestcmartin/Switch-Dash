package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

public class Player extends GameObject {

    private boolean isAlive_ = true;
    private boolean isWhite_ = true;
    private float sizeMult_ = 1f;
    private float width_;
    private float height_;
    private int gameWidth_;
    private int gameHeight_;

    public Player(Game g, Sprite s, int x, int y, int w, int h, int gameWidth, int gameHeight) {
        super(g, s, x, y, w, h);
        width_ = w;
        height_ = h;
        gameWidth_ = gameWidth;
        gameHeight_ = gameHeight;
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
        sizeMult_ = 0.8f;
    }

    private void updateSize(){
        w_ = (int)(sizeMult_ * width_);
        h_ = (int)(sizeMult_ * height_);
        x_ = (int)((gameWidth_ - w_) / 2);
        updateDstRect();
    }

    @Override
    public void update(double deltaTime) {
        if (sizeMult_ < 1f)
            sizeMult_ += deltaTime;
        else sizeMult_ = 1;
        updateSize();
    }

    @Override
    public void render(double deltaTime) {
        if(isAlive_)
            super.render(deltaTime);
    }
}
