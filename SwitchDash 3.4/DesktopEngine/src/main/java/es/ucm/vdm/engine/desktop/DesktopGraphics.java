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
    private JFrame window_;

    public DesktopGraphics(JFrame window) {
        this.window_ = window;

        int i = 100;
        while(i-- > 0) {
            try {
                window_.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        }
        if (i == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        strategy_ = window_.getBufferStrategy();
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
        graphics_ = strategy_.getDrawGraphics();
        graphics_.setColor(Color.BLUE);
        graphics_.fillRect(0, 0, getWidth(), getHeight());
        graphics_.dispose();
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        graphics_ = strategy_.getDrawGraphics();
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(), x, y, null);
        graphics_.dispose();
    }

    public void drawPixmap(Pixmap pixmap, Rect src, int x, int y) {
        graphics_ = strategy_.getDrawGraphics();
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                x, y, x + src.width(), y + src.height(),
                src.x1, src.y1, src.x2, src.y2,
                null);
        graphics_.dispose();
    }

    public void drawPixmap(Pixmap pixmap, Rect src, Rect dst) {
        graphics_ = strategy_.getDrawGraphics();
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                dst.x1, dst.y1, dst.x2, dst.y2,
                src.x1, src.y1, src.x2, src.y2,
                null);
        graphics_.dispose();
    }

    public int getWidth() {
        return window_.getWidth();
    }

    public int getHeight() {
        return window_.getHeight();
    }

    public BufferStrategy getBufferStrategy() { return strategy_; }
}
