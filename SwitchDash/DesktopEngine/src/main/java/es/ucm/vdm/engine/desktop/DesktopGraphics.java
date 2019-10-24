package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;
import java.awt.*;
import java.io.IOException;

public class DesktopGraphics implements Graphics{

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

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
}
