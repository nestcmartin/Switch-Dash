package es.ucm.vdm.engine.desktop;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DesktopWindow extends JFrame implements Runnable {

    private DesktopGame game_;
    private static String title_;
    private int width_;
    private int height_;
    private BufferStrategy strategy_;
    private volatile boolean running_ = true;

    public DesktopWindow(DesktopGame game, String title, int width, int height){
        super(title);
        game_ = game;
        title_ = title;
        width_ = width;
        height_ = height;
    }

    public void init(){
        setSize(width_, height_);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIgnoreRepaint(true);
        setVisible(true);

    }

    @Override
    public void run() {

        long lastFrameTime = System.nanoTime();

        while(running_) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double deltaTime = (double) nanoElapsedTime / 1.0E9;

            game_.getCurrentState().update(deltaTime);

            do {
                do {
                    try {
                        game_.getCurrentState().render(deltaTime);
                    }
                    finally {
                        //game_.getGraphics().dispose();
                    }
                } while(strategy_.contentsRestored());
                strategy_.show();
            } while(strategy_.contentsLost());
        }
    }
}
