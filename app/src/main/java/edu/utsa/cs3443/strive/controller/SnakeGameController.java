/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 *  SnakeGameController is a custom view that extends SurfaceView and handles the rendering and control of the snake game.
 *  It manages the game loop, processes user inputs for controlling the snake, and renders the game state onto the canvas.
 *  The controller interacts with the SnakeGame model to update the game state and responds to game events like game over.
 *  This class also includes touch event handling to control the snake's direction and manages the game's pause and resume states.
*/
package edu.utsa.cs3443.strive.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import edu.utsa.cs3443.strive.SnakeGameActivity;
import edu.utsa.cs3443.strive.model.SnakeGame;

public class SnakeGameController extends SurfaceView implements Runnable, SurfaceHolder.Callback  {
    private Thread gameThread;
    private SurfaceHolder surfaceHolder;
    private volatile boolean playing;
    private SnakeGame snakeGame;
    private Paint paint;
    private float startX;
    private float startY;
    private GameEndListener gameEndListener;
    private SnakeGameActivity mainActivity;

    public interface GameEndListener {
        void onGameEnd();
    }

    public SnakeGameController(Context context) {
        super(context);
        init(null, 0);
    }

    public SnakeGameController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SnakeGameController(Context context, SnakeGameActivity mainActivity) {
        super(context);
        this.mainActivity = mainActivity;
        init(null, 0);
    }

    public SnakeGameController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public SnakeGame getSnakeGame() {
        return snakeGame;
    }

    public void setMainActivity(SnakeGameActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Initializes the controller, sets up the game model, and prepares the view.
     *
     * @param attrs The AttributeSet used to configure the view.
     * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource.
     */
    private void init(AttributeSet attrs, int defStyleAttr) {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        snakeGame = new SnakeGame();
        paint = new Paint();
        snakeGame.setScoreUpdateListener(new SnakeGame.ScoreUpdateListener() {
            @Override
            public void onDefaultScoreReached() {
                if (mainActivity != null) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.returnToMainActivity();
                        }
                    });
                }
            }
        });

        setGameEndListener(new GameEndListener() {
            @Override
            public void onGameEnd() {
                if (mainActivity != null) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mainActivity, "Game Over! Tap to restart.", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }

    /**
     * Called when the surface is created, signaling that the game can start or resume.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        resume();
    }

    /**
     * Called when the surface dimensions change.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Called when the surface is destroyed, signaling that the game should pause.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        pause();
    }

    public int getCurrentScore() {
        return snakeGame.getScore();
    }

    public void setGameEndListener(GameEndListener listener) {
        this.gameEndListener = listener;
    }

    /**
     * The game loop method that runs on a separate thread.
     * It continuously updates the game state and renders the game onto the canvas.
     */
    @Override
    public void run() {
        while (playing) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    synchronized (surfaceHolder) {
                        snakeGame.updateGame();

                        if (!snakeGame.isGameOver()) {
                            drawGame(canvas);
                        } else {
                            handleGameOver();
                        }
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles rendering of the game's graphical elements on the canvas.
     *
     * @param canvas The Canvas on which the game elements are drawn.
     */
    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.GREEN);
        for (SnakeGame.Point point : snakeGame.getSnake()) {
            canvas.drawRect(point.getX() * 20, point.getY() * 20,
                    (point.getX() + 1) * 20, (point.getY() + 1) * 20, paint);
        }
        paint.setColor(Color.RED);
        SnakeGame.Point apple = snakeGame.getApple();
        canvas.drawRect(apple.getX() * 20, apple.getY() * 20,
                (apple.getX() + 1) * 20, (apple.getY() + 1) * 20, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("Score: " + snakeGame.getScore(), 10, 30, paint);

        int defaultHighScore = 5;
        canvas.drawText("High Score: " + defaultHighScore, 10, 60, paint);
    }

    /**
     * Handles the game over state, including stopping the game loop and triggering any game over events.
     */
    private void handleGameOver() {
        playing = false;
        if (gameEndListener != null) {
            gameEndListener.onGameEnd();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes touch events to control the direction of the snake.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (snakeGame.isGameOver()) {
            if (action == MotionEvent.ACTION_DOWN) {
                return true;
            } else if (action == MotionEvent.ACTION_UP) {
                restartGame();
                return true;
            }
        } else {
            if (action == MotionEvent.ACTION_DOWN) {
                startX = event.getX();
                startY = event.getY();
                return true;
            } else if (action == MotionEvent.ACTION_UP) {
                float endX = event.getX();
                float endY = event.getY();

                float deltaX = endX - startX;
                float deltaY = endY - startY;

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (deltaX > 0) {
                        snakeGame.setDirection(SnakeGame.Direction.RIGHT);
                    } else {
                        snakeGame.setDirection(SnakeGame.Direction.LEFT);
                    }
                } else {
                    if (deltaY > 0) {
                        snakeGame.setDirection(SnakeGame.Direction.DOWN);
                    } else {
                        snakeGame.setDirection(SnakeGame.Direction.UP);
                    }
                }
                performClick();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    /**
     * Restarts the game, resetting the score and game state, and starts a new game thread.
     */
    public void restartGame() {
        playing = true;
        snakeGame.resetScore();
        snakeGame.restartGame();
        if (gameThread != null) {
            gameThread.interrupt();
        }
        gameThread = new Thread(this);
        gameThread.start();
    }
}

