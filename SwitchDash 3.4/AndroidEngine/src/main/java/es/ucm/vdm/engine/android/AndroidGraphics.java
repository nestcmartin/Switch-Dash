package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Scaler;

public class AndroidGraphics implements Graphics {

    private AssetManager assets_;
    private Bitmap frameBuffer_;
    private Canvas canvas_;
    private Paint paint_;


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
            String[] imgPath = assets_.list(".");
            System.out.println(imgPath);
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

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), x, y, null);
    }

    public void drawPixmap(Pixmap pixmap, Rect src, int x, int y) {
        android.graphics.Rect source = new android.graphics.Rect(src.x1, src.y1, src.x2, src.y2);
        android.graphics.Rect destiny = new android.graphics.Rect(x, y, x + src.width(), y + src.height());
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), source, destiny, null);
    }

    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst) {
        dst = Scaler.scaleRect(dst);
        android.graphics.Rect source = new android.graphics.Rect(src.x1, src.y1, src.x2, src.y2);
        android.graphics.Rect destiny = new android.graphics.Rect(dst.x1, dst.y1, dst.x2, dst.y2);
        canvas_.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), source, destiny, null);
    }

    public int getWidth() {
        return frameBuffer_.getWidth();
    }

    public int getHeight() {
        return frameBuffer_.getHeight();
    }
}