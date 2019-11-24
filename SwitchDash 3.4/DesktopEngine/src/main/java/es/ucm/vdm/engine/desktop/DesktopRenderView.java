package es.ucm.vdm.engine.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

/**
 * Gestor de la ventana de Desktop y del bucle principal de juego en Desktop.
 * Extiende JFrame.
 */
public class DesktopRenderView extends JFrame {

    private DesktopGame game_;
    private volatile boolean running_ = true;

    /**
     * Constructora de clase. Inicializa la ventana de JFrame
     * @param game referencia al juego de Desktop que gestiona el bucle.
     * @param title el título de la ventana.
     * @param width el ancho de la ventana.
     * @param height el alto de la ventana.
     */
    public DesktopRenderView(DesktopGame game, String title, int width, int height) {
        super(title);
        this.game_ = game;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        // Añadimos un listener a la ventana, así cuando se reescala actualizamos el escalado de los gráficos
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if(game_.getGraphics() != null)
                    game_.getGraphics().scaleCanvas();
            }
        });
    }

    /**
     * Método que gestiona el bucle principal de juego.
     * Calcula el delta time y se encarga de actualizar la lógica y el renderizado del juego.
     */
    public void run() {
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
