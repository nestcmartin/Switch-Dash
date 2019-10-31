package es.ucm.vdm.engine;

public class Sprite {

    public Sprite(Pixmap image, Rect rect) {
        this.image_ = image;
        this.rect_ = rect;
    }

    //public void draw(Graphics g) {
    //    g.drawPixmap();
    //}

    private Pixmap image_;
    private Rect rect_;

}
