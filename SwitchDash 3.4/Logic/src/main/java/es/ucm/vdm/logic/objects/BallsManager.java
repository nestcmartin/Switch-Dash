package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Sound;
import es.ucm.vdm.engine.utils.Random;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObjct que se encarga de las bolas que van cayendo.
 * Tiene un array fijo de 10 bolas que van cayendo y
 * cuando el jugador se las carga vuelven arriba.
 */
public class BallsManager extends GameObject {

    public class Ball{
        public Ball(int y, int color){
            y_ = y;
            color_ = color;
        }
        public int color_;
        public float y_;
    }

    private Ball[] balls = new Ball[10];
    private int distanceBetweenBalls_= 395;
    private int pixelsPerSecond_ = 430;
    private int currentBall_ = 0;

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     * @param sprite sprite del GameObject
     * @param x coordenada x del GameObject en pantalla
     * @param y coordenada y del GameObject en pantalla
     * @param w ancho del GameObject con el que se debe renderizar
     * @param h alto del GameObject con el que se debe renderizar
     */
    public BallsManager(Game game, Sprite sprite, int x, int y, int w, int h) {
        super(game, sprite, x, y, w, h);

        // Create Balls
        int currentColor = randomColor(0);
        for(int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(0, 0);
            balls[i].y_ = i * -distanceBetweenBalls_;
            currentColor = randomColor(currentColor);
            balls[i].color_ = currentColor;
        }
    }


    /**
     * Actualiza todas las bolas desplazándolas en el eje y todas a la misma velocidad.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        for(Ball b: balls){
            b.y_ += pixelsPerSecond_ * deltaTime;
        }
    }


    /**
     * @return la coordenada y de la bola más abajo en la pantalla
     */
    public int getCurrentBallY(){
        return (int)(balls[currentBall_].y_ + sprite_.getHeight());
    }


    /**
     * @return el color de la bola más abajo en la pantalla (puede ser 0 o 1)
     */
    public int getCurrentBallColor(){
        return balls[currentBall_].color_;
    }


    /**
     * Se llama cuando el jugador ha acertado el color de la bola y tiene que "destruirse"
     * En realidad movemos la bola encima de la bola más alta y actualizamos la bola "actual"
     */
    public void correctBall(){

        int lastBallIndex = getCorrectIndex(currentBall_ + balls.length - 1);

        // Mueve esta bola detrás de la última
        balls[currentBall_].y_ = balls[lastBallIndex].y_ - distanceBetweenBalls_;

        // Le asigna un color aleatorio (basado en el de la última bola)
        balls[currentBall_].color_ = randomColor(balls[lastBallIndex].color_);

        // Current ball is now the next one in the array
        currentBall_ = getCorrectIndex(currentBall_ + 1);
    }


    /**
     * Se llama cuando la velocidad en la que bajan las bolas debe incrementarse
     * @param increment incremento de velovidad que se suma a la velocidad actual de las bolas
     */
    public void incrementSpeed(int increment){
        pixelsPerSecond_ += increment;
    }


    /**
     * Devuelve el indice indicado corregido por si se sale del array
     * @param i indice que se quiere corregir
     * @return el indice correcto, en caso de salirse del array empieza otra vez por el otro lado
     */
    private int getCorrectIndex(int i){
        if(i < balls.length)
            return i;
        else return i - balls.length;
    }


    /**
     * Devuelve un color aleatorio (0 o 1) basado en el de otra bola.
     * @param current color actual de una bola
     * @return un color aleatorio (0 o 1),
     *         con un 70% de probabilidades de ser el mismo que current.
     */
    private int randomColor(int current){
        if ((Random.randomInt(0, 100) < 70))
            return current ^ 1; // Devuelve el color contrario
        else
            return current;
    }


    /**
     * Renderiza todas las bolas en pantalla utilizando el mismo sprite y
     * cambiandolo de color según el color de la bola.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();

        for(Ball b: balls){
            if(b.y_ > -h_){
                sprite_.setFrameRow(b.color_);
                y_ = (int)b.y_;
                updateDstRect();
                sprite_.draw(g, dstRect_);
            }
        }
    }
}
