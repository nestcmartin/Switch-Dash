package es.ucm.vdm.engine.desktop;

import java.awt.Image;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class DesktopPixmap implements Pixmap {

    Image image_;
    Graphics.PixmapFormat format_;

    public DesktopPixmap(Image image, Graphics.PixmapFormat format) {
        this.image_ = image;
        this.format_ = format;
    }

    public int getWidth() {
        return image_.getWidth(null);
    }

    public int getHeight() {
        return image_.getHeight(null);
    }

    public Graphics.PixmapFormat getFormat() {
        return format_;
    }

    public void dispose() {
        image_.flush();
    }
}
