package es.ucm.vdm.engine.android;

import android.graphics.Bitmap;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class AndroidPixmap implements Pixmap {

    Bitmap bitmap_;
    Graphics.PixmapFormat format_;

    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap_ = bitmap;
        this.format_ = format;
    }

    public int getWidth() {
        return bitmap_.getWidth();
    }

    public int getHeight() {
        return bitmap_.getHeight();
    }

    public Graphics.PixmapFormat getFormat() {
        return format_;
    }

    public void dispose() {
        bitmap_.recycle();
    }
}