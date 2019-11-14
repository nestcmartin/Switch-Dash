package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class GameObject {

    protected int x_ = 0;
    protected int y_ = 0;
    protected Sprite sprite_;

    public GameObject(Pixmap image, Rect src) {
        sprite_ = new Sprite(image, src);
    }

    public void setPosition(int x, int y) {
        this.x_ = x;
        this.y_ = y;
    }

    public void setPositionX(int x) {
        this.x_ = x;
    }

    public void setPositionY(int y) {
        this.y_ = y;
    }

    public int getPositionX() {
        return x_;
    }

    public int getPositionY() {
        return y_;
    }

    public void update(double deltaTime) {};
    public void render(double deltaTime) {};
}
