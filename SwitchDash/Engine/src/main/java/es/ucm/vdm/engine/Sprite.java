package es.ucm.vdm.engine;

public class Sprite {

    public Sprite(Pixmap image, Rect rect) {
        this.image_ = image;
        this.rect_ = rect;
    }

    public void draw(Graphics g, Rect dst) {
        g.drawPixmap(image_, rect_, dst);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawPixmap(image_, rect_, x, y);
    }

    private Pixmap image_;
    private Rect rect_;

}
