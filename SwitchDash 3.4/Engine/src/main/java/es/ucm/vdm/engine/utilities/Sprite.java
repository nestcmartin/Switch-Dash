package es.ucm.vdm.engine.utilities;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class Sprite {

    private Rect srcRect_;
    private Rect dstRect_;
    private Pixmap image_;

    private int x_;
    private int y_;
    private int w_;
    private int h_;

    public int getX() { return x_; }
    public int getY() { return y_; }

    public void setX(int x) {
        x_ = x;
        dstRect_.x1 = x;
        dstRect_.x2 = x + w_;
    }

    public void setY(int y) {
        y_ = y;
        dstRect_.y1 = y;
        dstRect_.y2 = y + h_;
    }

    public Sprite(Pixmap image, Rect src, int x, int y, int w, int h) {
        this.image_ = image;
        this.srcRect_ = src;
        this.w_ = w;
        this.h_ = h;
        this.dstRect_ = new Rect();
        setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public void draw(Graphics g) {
        g.drawPixmap(image_, srcRect_, dstRect_);
    }

    public void draw(Graphics g, Rect dst) {
        g.drawPixmap(image_, srcRect_, dst);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawPixmap(image_, srcRect_, x, y);
    }



}