package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class GameObject {

    protected Game game_;

    protected int x_;
    protected int y_;
    protected int w_;
    protected int h_;

    protected Sprite sprite_;
    protected Rect dstRect_ = new Rect();

    public GameObject(Game g, Sprite s, int x, int y, int w, int h) {
        game_ = g;
        sprite_ = s;
        x_ = x;
        y_ = y;
        w_ = w;
        h_ = h;
    }

    public GameObject(Game g) {
        game_ = g;
        x_ = 0;
        y_ = 0;
        w_ = 0;
        h_ = 0;
    }

    public int getX() { return x_; }
    public int getY() { return y_; }
    public int getW() { return w_; }
    public int getH() { return h_; }

    public void setX(int x) { x_ = x; }
    public void setY(int y) { y_ = y; }
    public void setW(int w) { w_ = w; }
    public void setH(int h) { h_ = h; }
    public void setPosition(int x, int y) { setX(x); setY(y); }

    public void update(double deltaTime) {}

    protected void updateDstRect(){
        dstRect_.x1 = x_;
        dstRect_.y1 = y_;
        dstRect_.x2 = x_ + w_;
        dstRect_.y2 = y_ + h_;
    }

    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        sprite_.draw(g, dstRect_);
    }

}
