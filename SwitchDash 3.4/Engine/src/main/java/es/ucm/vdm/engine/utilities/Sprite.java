package es.ucm.vdm.engine.utilities;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class Sprite {

    private Rect srcRect_;
    private Pixmap image_;

    private int frameRow_ = 0;
    private int frameCol_ = 0;
    private int frameWidth_;
    private int frameHeight_;

    public Pixmap getImage() { return image_; }
    public int getFrameRow() { return frameRow_; }
    public int getFrameCol() { return frameCol_; }
    public void setFrameRow(int row) { frameRow_ = row; updateSourceRect(); }
    public void setFrameCol(int col) { frameCol_ = col; updateSourceRect(); }

    public Sprite(Pixmap image, int numRows, int numCols) {
        image_ = image;

        frameWidth_ = image_.getWidth() / numCols;
        frameHeight_ = image_.getHeight() / numRows;

        updateSourceRect();
    }

    public void draw(Graphics g, Rect dst) {
        g.drawPixmap(image_, srcRect_, dst);
    }

    private void updateSourceRect() {
        srcRect_ = new Rect(frameCol_ * frameWidth_,
                            frameRow_ * frameHeight_,
                           (frameCol_ * frameWidth_) + frameWidth_,
                           (frameRow_ * frameHeight_) + frameHeight_);
    }
}