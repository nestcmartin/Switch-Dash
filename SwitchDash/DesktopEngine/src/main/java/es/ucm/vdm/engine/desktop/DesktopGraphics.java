package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.Rect;

import java.awt.*;
import java.io.IOException;

public class DesktopGraphics implements Graphics {

    private java.awt.Graphics graphics_;

    public Pixmap newPixmap(String fileName) {
        Image image = null;
        try{
            image = javax.imageio.ImageIO.read(new java.io.File(fileName));
        }
        catch (IOException e){
            System.err.println("The image could not be loaded");
        }
        return new DesktopPixmap(image);
    }

    public void clear(int color) {
        graphics_.setColor(Color.BLACK);
        graphics_.fillRect(0, 0, getWidth(), getHeight());
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(), x, y, null);
    }

    public void drawPixmap(Pixmap pixmap, Rect src, int x, int y) {
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                x, y, x + src.width(), y + src.height(),
                src.x1, src.y1, src.x2, src.y2,
                null);
    }

    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst) {
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                dst.x1, dst.y1, dst.x2, dst.y2,
                src.x1, src.y1, src.x2, src.y2,
                null);
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
}
