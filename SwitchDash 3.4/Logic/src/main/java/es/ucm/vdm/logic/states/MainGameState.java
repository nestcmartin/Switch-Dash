package es.ucm.vdm.logic.states;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utilities.Pair;
import es.ucm.vdm.engine.utilities.PixmapManager;
import es.ucm.vdm.engine.utilities.Sprite;
import es.ucm.vdm.logic.Arrows;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.Background;
import es.ucm.vdm.logic.BallsManager;
import es.ucm.vdm.logic.FontMapper;
import es.ucm.vdm.logic.GameObject;
import es.ucm.vdm.logic.GameState;
import es.ucm.vdm.logic.Player;

public class MainGameState extends GameState {

    public final int GAME_WIDTH = 1080;
    public final int GAME_HEIGHT = 1920;

    private boolean gameOver = false;

    private Background background_;
    private Arrows arrows_;
    private Player player_;
    private BallsManager ballsManager_;

    private GameObject centenas_;
    private GameObject decenas_;
    private GameObject unidades_;

    private int speedIncrement_ = 90;
    private int correctBalls_ = 0;
    private int points_ = 0;

    public MainGameState(Game game) {
        super(game);
        ScaledGraphics g = game_.getGraphics();
        g.setCanvasLogicSize(GAME_WIDTH, GAME_HEIGHT);
        g.scaleCanvas();

        // Background
        background_ = new Background(game_);
        background_.updateColor();
        gameObjects_.add(background_);

        // Moving Arrows
        arrows_ = new Arrows(game_);
        gameObjects_.add(arrows_);

        // Score
        Sprite centenas = FontMapper.getInstance().getSprite(String.valueOf(0));
        Sprite decenas = FontMapper.getInstance().getSprite(String.valueOf(0));
        Sprite unidades = FontMapper.getInstance().getSprite(String.valueOf(0));
        centenas_ = new GameObject(game_, centenas, (GAME_WIDTH - (67 * 3)), 150, 67, 80);
        decenas_ = new GameObject(game_, decenas, (GAME_WIDTH - (67 * 2)), 150, 67, 80);
        unidades_ = new GameObject(game_, unidades, (GAME_WIDTH - 67), 150, 67, 80);
        gameObjects_.add(centenas_);
        gameObjects_.add(decenas_);
        gameObjects_.add(unidades_);

        Sprite playerSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.PLAYERS.ordinal()]), 2, 1);
        int playerX = (GAME_WIDTH - playerSprite.getWidth()) / 2;
        player_ = new Player(game_, playerSprite, playerX, 1200, playerSprite.getWidth(), playerSprite.getHeight());
        gameObjects_.add(player_);

        Sprite ballsSprite = new Sprite(PixmapManager.getInstance().getPixmap(Assets.images[Assets.ImageName.BALLS.ordinal()]), 2, 10);
        int ballX = (GAME_WIDTH - ballsSprite.getWidth()) / 2;
        ballsManager_ = new BallsManager(game_, ballsSprite, ballX, 0, ballsSprite.getWidth(), ballsSprite.getHeight());
        gameObjects_.add(ballsManager_);
    }

    @Override
    public void update(double deltaTime) {
        if(!gameOver)
            handleInput();

        super.update(deltaTime);

        if(!gameOver) {
            if (ballsManager_.getCurrentBallY() >= player_.getY()) {
                // Correct color
                if (player_.getColor() == ballsManager_.getCurrentBallColor()) {
                    ballsManager_.correctBall();
                    points_++;
                    correctBalls_++;
                    if (correctBalls_ >= 3) {
                        correctBalls_ = 0;
                        ballsManager_.incrementSpeed(speedIncrement_);
                        arrows_.incrementSpeed(speedIncrement_);
                    }
                }
                // Incorrect color
                else
                    gameOver();
            }
        }

        updateScore();
    }

    private void updateScore() {
        Pair c = FontMapper.getInstance().getFrameLocation(String.valueOf(points_ / 100));
        Pair d = FontMapper.getInstance().getFrameLocation(String.valueOf((points_ % 100) / 10));
        Pair u = FontMapper.getInstance().getFrameLocation(String.valueOf(points_ % 10));

        centenas_.updateSpriteFrame((int)c.second(), (int)c.first());
        decenas_.updateSpriteFrame((int)d.second(), (int)d.first());
        unidades_.updateSpriteFrame((int)u.second(), (int)u.first());
    }

    private void gameOver(){
        gameOver = true;
        player_.die();

        // ToDo: wait 1 second

        game_.setState(new GameOverState(game_, points_));
    }

    private void handleInput() {
        List<Input.KeyEvent> keyEvents = game_.getInput().getKeyEvents();
        List<Input.TouchEvent> touchEvents = game_.getInput().getTouchEvents();

        for (int i = 0; i < keyEvents.size(); i++) {
            Input.KeyEvent event = keyEvents.get(i);
            if(player_.handleKeyEvent(event)){}
        }

        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(player_.handleTouchEvent(event)){}
        }
    }

    @Override
    public void render(double deltaTime) {
        Graphics g = game_.getGraphics();
        g.clear(0xff000000);
        super.render(deltaTime);
    }
}
