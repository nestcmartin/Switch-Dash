package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.utilities.Sprite;

public class BallsManager extends GameObject {

    public class Ball{
        public Ball(int y, int color){
            y_ = y;
            color_ = color;
        }
        public int color_;
        public int y_;
    }

    private Ball[] balls = new Ball[10];
    private int distanceBetweenBalls_= 395;

    public BallsManager(Game g, Sprite s, int x, int y, int w, int h) {
        super(g, s, x, y, w, h);
        for(int i = 0; i < balls.length; i++)
            balls[i] = new Ball(0, 0);
    }

    @Override
    public void update(double deltaTime) {
        for(int i = 0; i < balls.length; i++){
            balls[i].y_ = distanceBetweenBalls_ * i;
        }
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();

        for(Ball b: balls){
            sprite_.setFrameRow(b.color_);
            y_ = b.y_;
            updateDstRect();
            sprite_.draw(g, dstRect_);
        }
    }
}
