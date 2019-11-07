package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.utilities.Rect;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

public class DesktopGraphics implements Graphics {

    private java.awt.Graphics graphics_;
    private BufferStrategy strategy_;

    public DesktopGraphics(DesktopWindow window) {

        int intentos = 100;
        while(intentos-- > 0) {
            try {
                window.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
                System.out.println(intentos);
            }
        }

        if (intentos == -1) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        strategy_ = window.getBufferStrategy();
        graphics_ = strategy_.getDrawGraphics();
    }

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

    public void dispose() {
        graphics_.dispose();
    }
}
