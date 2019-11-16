package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utils.Random;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.GameObject;

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
    private int currentColor_ = 0;

    public BallsManager(Game g, Sprite s, int x, int y, int w, int h) {
        super(g, s, x, y, w, h);

        // Create Balls
        for(int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(0, 0);
            balls[i].y_ = i * -distanceBetweenBalls_;
            randomColor();
            balls[i].color_ = currentColor_;
        }
    }

    @Override
    public void update(double deltaTime) {
        for(Ball b: balls){
            b.y_ += pixelsPerSecond_ * deltaTime;
        }
    }

    public int getCurrentBallY(){
        return (int)(balls[currentBall_].y_ + sprite_.getHeight());
    }

    public int getCurrentBallColor(){
        return balls[currentBall_].color_;
    }

    public void correctBall(){
        int lastBallIndex = getLoopIndex(currentBall_ + balls.length - 1);

        // Move this ball behind the last one (plus distanceBetweenBalls)
        balls[currentBall_].y_ = balls[lastBallIndex].y_ - distanceBetweenBalls_;

        // Current ball is now the next one in the array
        currentBall_ = getLoopIndex(currentBall_ + 1);
    }

    public void incrementSpeed(int increment){
        pixelsPerSecond_ += increment;
    }

    private int getLoopIndex(int i){
        if(i < balls.length)
            return i;
        else return i - balls.length;
    }

    private void randomColor(){
        if ((Random.randomInt(0, 100) < 70))
            currentColor_ = currentColor_ ^ 1;
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();

        for(Ball b: balls){
            sprite_.setFrameRow(b.color_);
            y_ = (int)b.y_;
            updateDstRect();
            sprite_.draw(g, dstRect_);
        }
    }
}
