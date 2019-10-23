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

    AssetManager assets_;
    Bitmap frameBuffer_;
    Canvas canvas_;
    Paint paint_;
    Rect srcRect_ = new Rect();
    Rect dstRect_ = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets_ = assets;
        this.frameBuffer_ = frameBuffer;
        this.canvas_ = new Canvas(frameBuffer_);
        this.paint_ = new Paint();

    }

    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Bitmap.Config config = null;
        if (format == PixmapFormat.RGB565) {
            config = Bitmap.Config.RGB_565;
        }
        else if (format == PixmapFormat.ARGB4444) {
            config = Bitmap.Config.ARGB_4444;
        }
        else {
            config = Bitmap.Config.ARGB_8888;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig  = config;
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets_.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) {
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
        }
        if (bitmap.getConfig() == Bitmap.Config.RGB_565) {
            format = PixmapFormat.RGB565;
        }
        else if (bitmap.getConfig() == Bitmap.Config.ARGB_4444) {
            format = PixmapFormat.ARGB4444;
        }
        else {
            format = PixmapFormat.ARGB8888;
        }
        return new AndroidPixmap(bitmap, format);
    }

    public void clear(int color) {
        canvas_.drawRGB((color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                (color & 0xff));
    }

    public void drawPixel(int x, int y, int color) {
        paint_.setColor(color);
        canvas_.drawPoint(x, y, paint_);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint_.setColor(color);
        canvas_.drawLine(x, y, x2, y2, paint_);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        paint_.setColor(color);
        paint_.setStyle(Paint.Style.FILL);
        canvas_.drawRect(x, y, x + width - 1, y + height - 1, paint_);
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
        canvas_.drawBitmap(((AndroidPixmap)pixmap).bitmap_, srcRect_, dstRect_, null);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas_.drawBitmap(((AndroidPixmap)pixmap).bitmap_, x, y, null);
    }

    public int getWidth() {
        return frameBuffer_.getWidth();
    }

    public int getHeight() {
        return frameBuffer_.getHeight();
    }
}