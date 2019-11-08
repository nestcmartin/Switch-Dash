package es.ucm.vdm.engine.utilities;

public class Scaler {

    private static int windowWidth_;
    private static int windowHeight_;
    private static int canvasLogicWidth_ = 1080;
    private static int canvasLogicHeight_ = 2220;
    private static int canvasWidth_;
    private static int canvasHeight_;
    private static int canvasPosX_;
    private static int canvasPosY_;
    private static float aspectRatio_;

    public static void scaleCanvas(int ww, int wh) {
        windowWidth_ = ww;
        windowHeight_ = wh;
        aspectRatio_ = (float)ww / (float)wh;

        if (windowWidth_ >= windowHeight_)
        {
            canvasHeight_ = windowHeight_;
            canvasWidth_ = (int)((float) canvasHeight_ / aspectRatio_);
            canvasPosX_ = (windowWidth_ - canvasWidth_) / 2;
            canvasPosY_ = 0;
        }
        else
        {
            canvasWidth_ = windowWidth_;
            canvasHeight_ = (int)((float) canvasWidth_ * aspectRatio_);
            canvasPosY_ = (windowHeight_ - canvasHeight_) / 2;
            canvasPosX_ = 0;
        }

        System.out.println((float)canvasWidth_ / (float)canvasHeight_);
    }

    private static int scaleInAxisX(int x) {
        return canvasWidth_ * x / canvasLogicWidth_;
    }

    private static int scaleInAxisY(int y) {
        return canvasHeight_ * y / canvasLogicHeight_;
    }

    public static Rect scaleRect(Rect rect) {
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
