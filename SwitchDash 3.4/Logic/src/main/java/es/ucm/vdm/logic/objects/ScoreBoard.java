package es.ucm.vdm.logic.objects;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.utils.Pair;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.FontMapper;
import es.ucm.vdm.logic.GameObject;


/**
 * GameObject que se encarga de renderizar la puntuación del juego
 */
public class ScoreBoard extends GameObject {

    private GameObject centenas_;
    private GameObject decenas_;
    private GameObject unidades_;

    private int score_ = 0;

    /**
     * Constructora de clase
     * @param game referencia al juego de Game que gestiona el bucle
     * @param fontW ancho al que se tiene que renderizar un caracter del marcador
     * @param fontH alto al que se tiene que renderizar un caracter del marcador
     */
    public ScoreBoard(Game game, int fontW, int fontH) {
        super(game);

        Sprite centenas = FontMapper.getInstance().getSprite(String.valueOf(0));
        Sprite decenas = FontMapper.getInstance().getSprite(String.valueOf(0));
        Sprite unidades = FontMapper.getInstance().getSprite(String.valueOf(0));

        centenas_ = new GameObject(game_, centenas, (1080 - (int)(fontW * 2.5f)), 150, fontW, fontH);
        decenas_ = new GameObject(game_, decenas, (1080 - (int)(fontW * 1.75f)), 150, fontW, fontH);
        unidades_ = new GameObject(game_, unidades, (1080 - fontW), 150, fontW, fontH);
    }

    @Override
    public void update(double deltaTime) {
        centenas_.update(deltaTime);
        decenas_.update(deltaTime);
        unidades_.update(deltaTime);
    }


    /**
     * Renderiza solamente los números necesarios para el marcador
     * @param deltaTime tiempo transcurrido desde la última actualización.
     */
    @Override
    public void render(double deltaTime) {
        unidades_.render(deltaTime);
        if(score_ > 9)
            decenas_.render(deltaTime);
        if(score_ > 99)
            centenas_.render(deltaTime);
    }

    /**
     * @return la puntuación actual de la partida
     */
    public int getScore() {
        return score_;
    }

    /**
     * Incrementa el marcador en 1 y actualiza los sprites correspondientes.
     */
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
