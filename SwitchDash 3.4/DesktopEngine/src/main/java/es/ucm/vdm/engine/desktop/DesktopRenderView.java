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

        // Añadimos un listener a la ventana, así cuando se reescala actualizamos el escalado de los gráficos
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if(game_.getGraphics() != null)
                    game_.getGraphics().scaleCanvas(game_.getGraphics().getWidth(), game_.getGraphics().getHeight());
            }
        });
    }

    public void run() {

        game_.getGraphics().scaleCanvas(game_.getGraphics().getWidth(), game_.getGraphics().getHeight());

        long startTime = System.nanoTime();

        while(running_) {
            double deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game_.getCurrentState().update(deltaTime);

            do {
                do {
                    game_.getGraphics().setGraphics();
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
