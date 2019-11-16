package es.ucm.vdm.engine;

import es.ucm.vdm.engine.utils.Rect;

public abstract class ScaledGraphics implements Graphics {

    private int canvasLogicWidth_ = 1080;
    private int canvasLogicHeight_ = 2220;
    private int canvasWidth_;
    private int canvasHeight_;
    private int canvasPosX_;
    private int canvasPosY_;

    public int getCanvasLogicHeight() { return canvasLogicHeight_; }
    public int getCanvasLogicWidth() { return  canvasLogicWidth_; }

    @Override
    public void fillRect(Rect rect, int color) {
        rect = scaleRect(rect);
        fillScaledRect(rect, color);
    }

    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha) {
        dst = scaleRect(dst);
        drawScaledPixmap(pixmap, src, dst, alpha);
    }

    protected void drawScaledPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha){};
    protected void fillScaledRect(Rect rect, int color) {};

    public void setCanvasLogicSize(int w, int h) {
        canvasLogicWidth_ = w;
        canvasLogicHeight_ = h;
    }

    public void scaleCanvas() {
        int wh = getHeight();
        int ww = getWidth();
        float logicAspectRatio = (float)canvasLogicHeight_ / (float)canvasLogicWidth_;
        float realAspectRatio = (float)wh / (float)ww;

        if (logicAspectRatio > realAspectRatio)
        {
            canvasHeight_ = wh;
            canvasWidth_ = (int)((float)canvasHeight_ / logicAspectRatio);
            canvasPosX_ = (ww - canvasWidth_) / 2;
            canvasPosY_ = 0;
        }
        else
        {
            canvasWidth_ = ww;
            canvasHeight_ = (int)((float)canvasWidth_ * logicAspectRatio);
            canvasPosY_ = (wh - canvasHeight_) / 2;
            canvasPosX_ = 0;
        }
    }

    private int scaleInAxisX(int x) {
        return canvasWidth_ * x / canvasLogicWidth_;
    }

    private int scaleInAxisY(int y) {
        return canvasHeight_ * y / canvasLogicHeight_;
    }

    public Rect scaleRect(Rect rect) {
        Rect r = new Rect(0, 0, 0,0);
        int newX1 = scaleInAxisX(rect.x1);
        int newY1 = scaleInAxisY(rect.y1);
        int newX2 = scaleInAxisX(rect.x2);
        int newY2 = scaleInAxisY(rect.y2);
        r.x1 = newX1 + canvasPosX_;
        r.x2 = newX2 + canvasPosX_;
        r.y1 = newY1 + canvasPosY_;
        r.y2 = newY2 + canvasPosY_;
        return r;
    }
}
