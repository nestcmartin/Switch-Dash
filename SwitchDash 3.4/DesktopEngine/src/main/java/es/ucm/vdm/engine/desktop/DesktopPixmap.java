package es.ucm.vdm.engine.desktop;

import java.awt.Image;
import es.ucm.vdm.engine.Pixmap;

public class DesktopPixmap implements Pixmap {

    private Image image_;

    public Image getImage() {
        return image_;
    }

    public DesktopPixmap(Image image) {
        this.image_ = image;
    }

    public int getWidth() {
        return image_.getWidth(null);
    }

    public int getHeight() {
        return image_.getHeight(null);
    }

    public void dispose() {
        image_.flush();
    }
}
