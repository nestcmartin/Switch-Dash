package es.ucm.vdm.engine.desktop;

import javax.swing.JFrame;

public class DesktopWindow extends JFrame implements Runnable {

    private static String title_;
    private DesktopGame game_;
    private volatile boolean running_ = false;

    private int width_;
    private int height_;

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
    }

    @Override
    public void run() {
        init();
        this.setVisible(true);

        long startTime = System.nanoTime();
        running_ = true;

        while(running_) {

            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game_.getCurrentState().update(deltaTime);
            game_.getCurrentState().render(deltaTime);
        }
    }
}
