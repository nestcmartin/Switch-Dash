package es.ucm.vdm.engine.desktop;

import es.ucm.vdm.engine.Pixmap;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utils.Rect;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

public class DesktopGraphics extends ScaledGraphics {

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
                System.err.println("Error creando BufferStrategy; reintentando...");
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
            image = javax.imageio.ImageIO.read(new java.io.File(DesktopGame.assetsPath_ + fileName));
        }
        catch (IOException e){
            System.err.println("The image could not be loaded");
        }
        return new DesktopPixmap(image);
    }

    private Color intToColor(int color){
        int r = (color>>16)&0xFF;
        int g = (color>>8)&0xFF;
        int b = (color>>0)&0xFF;
        return new Color(r, g, b);
    }

    public void clear(int color) {
        graphics_.setColor(intToColor(color));
        graphics_.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void fillScaledRect(Rect rect, int color) {
        graphics_.setColor(intToColor(color));
        graphics_.fillRect(rect.x1, rect.y1, rect.width(), rect.height());
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

    @Override
    protected void drawScaledPixmap(Pixmap pixmap, Rect src, Rect dst, float alpha) {
        if(alpha == 1) {
            drawPixmapBasic(pixmap, src, dst);
        }
        else {
            Graphics2D graphics2D = (Graphics2D)graphics_;
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            drawPixmapBasic(pixmap, src, dst);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }

    private void drawPixmapBasic(Pixmap pixmap, Rect src, Rect dst){
        graphics_.drawImage(((DesktopPixmap)pixmap).getImage(),
                dst.x1, dst.y1, dst.x2, dst.y2,
                src.x1, src.y1, src.x2, src.y2,
                null);
    }

    public int getWidth() {
        return window_.getWidth();
    }

    public int getHeight() {
        return window_.getHeight();
    }

    public void setGraphics(){
        while(true) {
            try {
                graphics_ = strategy_.getDrawGraphics();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dispose() {
        graphics_.dispose();
    }

    public BufferStrategy getBufferStrategy() { return strategy_; }

}
