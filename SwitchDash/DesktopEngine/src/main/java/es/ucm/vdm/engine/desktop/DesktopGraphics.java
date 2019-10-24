package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;

public class DesktopGraphics implements Graphics {

    private java.awt.Graphics graphics_;

    public Pixmap newPixmap(String fileName) {
        return null;
    }

    public void clear(int color) {

    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcW, int srcH) {

    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {

    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
}
