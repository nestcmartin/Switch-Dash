package es.ucm.vdm.engine.android;

import android.graphics.Bitmap;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class AndroidPixmap implements Pixmap {

    private Bitmap bitmap_;

    public Bitmap getBitmap() {
        return bitmap_;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap_ = bitmap;
    }

    public AndroidPixmap(Bitmap bitmap) {
        this.bitmap_ = bitmap;
    }

    public int getWidth() {
        return bitmap_.getWidth();
    }

    public int getHeight() {
        return bitmap_.getHeight();
    }

    public void dispose() {
        bitmap_.recycle();
    }
}