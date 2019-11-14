package es.ucm.vdm.engine.desktop;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class DesktopRenderView extends JFrame {

    private DesktopGame game_;
    private volatile boolean running_ = true;

    public DesktopRenderView(DesktopGame game, String title, int width, int height) {
        super(title);
        this.game_ = game;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        // Añadimos un listener para que cuando se reescale la ventana el escalado se actualice
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if(game_.getGraphics() != null)
                    game_.getGraphics().scaleCanvas(game_.getGraphics().getWidth(), game_.getGraphics().getHeight());
            }
        });
    }

    public void run() {

        game_.getGraphics().scaleCanvas(game_.getGraphics().getWidth(), game_.getGraphics().getHeight());

        long lastFrameTime = System.nanoTime();

        while(running_) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double deltaTime = (double) nanoElapsedTime / 1.0E9;

            game_.getCurrentState().update(deltaTime);

            do {
                do {
                    game_.getGraphics().setGraphics();
                    //Scaler.scaleCanvas(game_.getGraphics().getWidth(), game_.getGraphics().getHeight());
                    try {
                        game_.getCurrentState().render(deltaTime);
                    } finally {
                        game_.getGraphics().dispose();
                    }
                } while(game_.getGraphics().getBufferStrategy().contentsRestored());
                game_.getGraphics().getBufferStrategy().show();
            } while(game_.getGraphics().getBufferStrategy().contentsLost());
        }
    }
}
