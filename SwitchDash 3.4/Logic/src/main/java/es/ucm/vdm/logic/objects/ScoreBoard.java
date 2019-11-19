package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.utils.Pair;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.FontMapper;
import es.ucm.vdm.logic.GameObject;

public class ScoreBoard extends GameObject {

    private GameObject centenas_;
    private GameObject decenas_;
    private GameObject unidades_;

    private int score_ = 0;

    public ScoreBoard(Game g) {
        super(g);

        Sprite centenas = FontMapper.getInstance().getSprite(String.valueOf(0));
        Sprite decenas = FontMapper.getInstance().getSprite(String.valueOf(0));
        Sprite unidades = FontMapper.getInstance().getSprite(String.valueOf(0));

        centenas_ = new GameObject(game_, centenas, (1080 - (93 * 3)), 150, 93, 112);
        decenas_ = new GameObject(game_, decenas, (1080 - (93 * 2)), 150, 93, 112);
        unidades_ = new GameObject(game_, unidades, (1080 - 93), 150, 93, 112);
    }

    @Override
    public void update(double deltaTime) {
        centenas_.update(deltaTime);
        decenas_.update(deltaTime);
        unidades_.update(deltaTime);
    }

    @Override
    public void render(double deltaTime) {
        unidades_.render(deltaTime);
        if(score_ > 9)
            decenas_.render(deltaTime);
        if(score_ > 99)
            centenas_.render(deltaTime);
    }

    public int getScore() {
        return score_;
    }

    public void incrementScore() {
        score_++;

        Pair c = FontMapper.getInstance().getFrameLocation(String.valueOf(score_ / 100));
        Pair d = FontMapper.getInstance().getFrameLocation(String.valueOf((score_ % 100) / 10));
        Pair u = FontMapper.getInstance().getFrameLocation(String.valueOf(score_ % 10));

        centenas_.updateSpriteFrame((int)c.second(), (int)c.first());
        decenas_.updateSpriteFrame((int)d.second(), (int)d.first());
        unidades_.updateSpriteFrame((int)u.second(), (int)u.first());
    }
}
