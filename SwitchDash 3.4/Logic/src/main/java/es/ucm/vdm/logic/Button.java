package es.ucm.vdm.logic;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.utilities.Rect;
import es.ucm.vdm.engine.utilities.Sprite;

public class Button extends GameObject {

    private Rect scaledRect_;

    public Button(Game g, Sprite s, int x, int y, int w, int h) {
        super(g, s, x, y, w, h);
    }

    @Override
    protected void updateDstRect() {
        super.updateDstRect();
        scaledRect_ = game_.getGraphics().scaleRect(dstRect_);
    }

    public boolean handleTouchEvent(Input.TouchEvent event){
        return (event.type_ == Input.EventType.RELEASED && isInsideButton(event.x_, event.y_));
    }

    private boolean isInsideButton(int x, int y){
        return x > scaledRect_.x1 &&
               x < scaledRect_.x2 &&
               y > scaledRect_.y1 &&
               y < scaledRect_.y2;
    }
}
