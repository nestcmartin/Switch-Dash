package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class AndroidGraphics implements Graphics {

    private AssetManager assets_;
    private Bitmap frameBuffer_;
    private Canvas canvas_;
    private Paint paint_;
    private Rect srcRect_ = new Rect();
    private Rect dstRect_ = new Rect();


    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets_ = assets;
        this.frameBuffer_ = frameBuffer;
        this.canvas_ = new Canvas(frameBuffer_);
        this.paint_ = new Paint();

    }

    public Pixmap newPixmap(String fileName) {
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets_.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) {
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return new AndroidPixmap(bitmap);
    }

    public void clear(int color) {
        canvas_.drawRGB((color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                (color & 0xff));
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcW, int srcH) {
        srcRect_.left = srcX;
        srcRect_.top = srcY;
        srcRect_.right = srcX + srcW - 1;
        srcRect_.bottom = srcY + srcH - 1;

        dstRect_.left = x;
        dstRect_.top = y;
        dstRect_.right = x + srcW - 1;
        dstRect_.bottom = y + srcH - 1;
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), srcRect_, dstRect_, null);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), x, y, null);
    }

    public int getWidth() {
        return frameBuffer_.getWidth();
    }

    public int getHeight() {
        return frameBuffer_.getHeight();
    }
}