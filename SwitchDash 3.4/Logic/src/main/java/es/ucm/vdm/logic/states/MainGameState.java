package es.ucm.vdm.logic.states;

import java.util.List;

import es.ucm.vdm.engine.Game;
import es.ucm.vdm.engine.Graphics;
import es.ucm.vdm.engine.Input;
import es.ucm.vdm.engine.ScaledGraphics;
import es.ucm.vdm.engine.utils.AudioManager;
import es.ucm.vdm.engine.utils.PixmapManager;
import es.ucm.vdm.engine.utils.Sprite;
import es.ucm.vdm.logic.objects.Arrows;
import es.ucm.vdm.logic.Assets;
import es.ucm.vdm.logic.objects.Background;
import es.ucm.vdm.logic.objects.BallsManager;
import es.ucm.vdm.logic.GameState;
import es.ucm.vdm.logic.objects.Player;
import es.ucm.vdm.logic.objects.ScoreBoard;

public class MainGameState extends GameState {

    private boolean gameOver = false;

    private Background background_;
    private Arrows arrows_;
    private Player player_;
    private BallsManager ballsManager_;
    private ScoreBoard scoreBoard_;


    private int speedIncrement_ = 90;
    private int correctBalls_ = 0;

    public MainGameState(Game game) {
        super(game);
        ScaledGraphics g = game_.getGraphics();
        g.setCanvasLogicSize(GAME_WIDTH, GAME_HEIGHT);
        g.scaleCanvas();

        AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.MENU_MUSIC.ordinal()]).stop();
        AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.GAME_MUSIC.ordinal()]).play();

        // Background
        background_ = new Background(game_);
        background_.updateColor();
        gameObjects_.add(background_);

        // Moving Arrows
        arrows_ = new Arrows(game_);
        gameObjects_.add(arrows_);

        // Score
        scoreBoard_ = new ScoreBoard(game_);
        gameObjects_.add(scoreBoard_);

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
                    scoreBoard_.incrementScore();
                    correctBalls_++;
                    if (correctBalls_ >= 10) {
                        correctBalls_ = 0;
                        ballsManager_.incrementSpeed(speedIncrement_);
                        arrows_.incrementSpeed(speedIncrement_);
                        background_.updateColor();
                    }
                }
                // Incorrect color
                else
                    gameOver();
            }
        }
    }

    private void gameOver(){
        gameOver = true;
        player_.die();

        // ToDo: wait 1 second
        AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.GAME_MUSIC.ordinal()]).stop();
        AudioManager.getInstance().getMusic(Assets.musics[Assets.MusicName.MENU_MUSIC.ordinal()]).play();
        game_.setState(new GameOverState(game_, scoreBoard_.getScore()));
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
