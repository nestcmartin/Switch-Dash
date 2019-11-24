package es.ucm.vdm.logic.objects;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Pool;
import es.ucm.vdm.engine.utils.PoolObjectFactory;
import es.ucm.vdm.engine.utils.Random;
import es.ucm.vdm.engine.utils.Rect;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que se encarga del sistema de partículas que se usa cuando se destruye una bola
 */
public class ParticleEmitter extends GameObject {

    /**
     * Clase auxiliar para las partículas
     */
    class Particle{
        public Particle(){}

        /**
         * @param x coordenada x de la partícula en la pantalla
         * @param y coordenada y de la partícula en la pantalla
         * @param size tamaño de la partícula en la pantalla
         * @param speedX velocidad en el eje x de la partícula
         * @param speedY velocidad en el eje y de la partícula
         * @param color color de la partícula (0 o 1)
         * @param age edad de la particula
         */
        public void setParams(int x, int y, int size, float speedX, float speedY, int color, float age) {
            x_ = x;
            y_ = y;
            size_ = size;
            speedX_ = speedX;
            speedY_ = speedY;
            color_ = color;
            age_ = age;
            dstRect_ = new Rect();
            updateRect();
        }

        public void updateRect(){
            dstRect_.x1 = (int)x_;
            dstRect_.y1 = (int)y_;
            dstRect_.x2 = (int)(x_ + size_);
            dstRect_.y2 = (int)(y_ + size_);
        }

        public float x_;
        public float y_;
        public int size_;
        public float speedX_;
        public float speedY_;
        public int color_;
        public float age_;
        public Rect dstRect_;
    }

    private int gravity_;
    private float maxAge_;
    private int xSpeed_;
    private int ySpeed_;
    protected List<Particle> particles_ = new ArrayList<>();
    protected List<Particle> deadParticles_ = new ArrayList<>();
    private Pool<Particle> particlesPool_;

     /**
     * Constructora de clase.
     * @param game referencia al juego de Game que gestiona el bucle.
     * @param sprite sprite del GameObject
     * @param x coordenada x del GameObject en pantalla
     * @param y coordenada y del GameObject en pantalla
     * @param w ancho del GameObject con el que se debe renderizar
     * @param h alto del GameObject con el que se debe renderizar
     * @param maxAge edad máxima de las partículas (en segundos)
     * @param gravity gravidad que se aplicará a las partículas
     * @param xSpeed velocidad inicial de las partículas en el eje x
     * @param ySpeed velocidad inicial de las partículas en el eje y
     */
    public ParticleEmitter(Game game, Sprite sprite, int x, int y, int w, int h, float maxAge, int gravity, int xSpeed, int ySpeed) {
        super(game, sprite, x, y, w, h);
        maxAge_ = maxAge;
        gravity_ = gravity;
        xSpeed_ = xSpeed;
        ySpeed_ = ySpeed;

        // Pool
        PoolObjectFactory<Particle> factory = new PoolObjectFactory<Particle>() {
            @Override
            public Particle createObject() {
                return new Particle();
            }
        };
        particlesPool_ = new Pool<Particle>(factory, 100);
    }


    /**
     * Emite una ráfaga de partículas en la dirección y velocidad inicial especificados
     * en la constructora. Varia ligeramente la velocidad y el tamaño inicial de cada
     * partícula para darles variedad.
     * @param nParticles número de partículas deseadas en la ráfaga
     * @param color color de las partículas (0 o 1)
     */
    public void burst(int nParticles, int color){
        for(int i = 0; i < nParticles; i++){
            Particle p = particlesPool_.newObject();
            p.setParams(x_, y_ ,
                    Random.randomInt(40, 80),
                    Random.randomInt(-xSpeed_, xSpeed_),
                    Random.randomInt(-ySpeed_, -ySpeed_/4),
                    color, 0);
            particles_.add(p);
        }
    }


    /**
     * Actualiza todas las partículas, les aplica gravedad, actualiza su velocidad y edad.
     * Si una partícula supera la edad máxima, la partícula muere y vuelve al pool.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double deltaTime) {
        for(Particle p: particles_) {
            p.speedY_ -= gravity_ * deltaTime;
            p.x_ += p.speedX_ * deltaTime;
            p.y_ += p.speedY_ * deltaTime;
            p.age_ += deltaTime;
            p.updateRect();
            if(p.age_ > maxAge_)
                deadParticles_.add(p);
        }

        while (!deadParticles_.isEmpty()){
            Particle p = deadParticles_.get(0);
            particles_.remove(p);
            deadParticles_.remove(p);
            particlesPool_.free(p);
        }
    }

    /**
     * Renderiza todas las partículas en pantalla utilizando el mismo sprite,
     * cambiando el color de cada una a su color correcto.
     * Utiliza su edad como alpha, siendo que 0 es nada transparente y
     * maxAge es totalmente transparente.
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();

        for(Particle p: particles_){
            if(p.age_ > 0){
                sprite_.setFrameRow(p.color_);
                sprite_.draw(g, p.dstRect_, (maxAge_ - p.age_)/maxAge_);
            }
        }
    }
}
