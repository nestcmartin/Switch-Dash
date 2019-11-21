package es.ucm.vdm.logic.objects;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Random;
import es.ucm.vdm.engine.utils.Rect;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

public class ParticleEmitter extends GameObject {

    class Particle{
        public Particle(int x, int y, int size, float speedX, float speedY, int color, float age) {
            x_ = x;
            y_ = y;
            size_ = size;
            speedX_ = speedX;
            speedY_ = speedY;
            color_ = color;
            age_ = age;
            dstRect_ = new Rect(x_, y_, x_+ size_, y + size_);
        }

        public void updateRect(){
            dstRect_.x1 = x_;
            dstRect_.y1 = y_;
            dstRect_.x2 = x_ + size_;
            dstRect_.y2 = y_ + size_;
        }

        public int x_;
        public int y_;
        public int size_;
        public float speedX_;
        public float speedY_;
        public int color_;
        public float age_;
        public Rect dstRect_;
    }

    private int gravity_;
    private float startAge_;
    private int xSpeed_;
    private int ySpeed_;
    protected List<Particle> particles_ = new ArrayList<>();
    protected List<Particle> deadParticles_ = new ArrayList<>();


    public ParticleEmitter(Game g, Sprite s, int x, int y, int w, int h, float startAge, int gravity, int xSpeed, int ySpeed) {
        super(g, s, x, y, w, h);
        startAge_ = startAge;
        gravity_ = gravity;
        xSpeed_ = xSpeed;
        ySpeed_ = ySpeed;
        // ToDo: Implementar el pool
    }

    public void burst(int nParticles, int color){
        for(int i = 0; i < nParticles; i++)
            particles_.add(new Particle(x_, y_ , Random.randomInt(40, 80),Random.randomInt(-xSpeed_, xSpeed_),
                                                Random.randomInt(-ySpeed_, -ySpeed_/4), color, startAge_));
    }

    @Override
    public void update(double deltaTime) {
        for(Particle p: particles_) {
            p.speedY_ -= gravity_ * deltaTime;
            p.x_ += p.speedX_ * deltaTime;
            p.y_ += p.speedY_ * deltaTime;
            p.age_ -= deltaTime;
            p.updateRect();
            if(p.age_ < 0)
                deadParticles_.add(p);
        }

        while (!deadParticles_.isEmpty()){
            Particle p = deadParticles_.get(0);
            particles_.remove(p);
            deadParticles_.remove(p);
        }
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();

        for(Particle p: particles_){
            if(p.age_ > 0){
                sprite_.setFrameRow(p.color_);
                sprite_.draw(g, p.dstRect_, p.age_/startAge_);
            }
        }
    }
}
