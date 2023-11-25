package edu.utsa.cs3443.strive.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

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
    public interface GameEndListener {
        void onGameEnd();
    }
    public void setGameEndListener(GameEndListener listener) {
        this.gameEndListener = listener;
    }

    public SnakeGameController(Context context) {
        super(context);
        init(null, 0);
    }

    public SnakeGameController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        pause();
    }

    public SnakeGameController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        snakeGame = new SnakeGame();
        paint = new Paint();
    }

    public int getCurrentScore() {
        return snakeGame.getScore();
    }


    @Override
    public void run() {
        Log.d("SnakeGameController", "Starting game loop");
        while (playing) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    synchronized (surfaceHolder) {
                        if (!snakeGame.isGameOver()) {
                            drawGame(canvas);
                        }
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            snakeGame.updateGame();
            if (snakeGame.isGameOver()) {
                playing = false; // Stop the game loop
                Log.d("SnakeGameController", "Game over detected in game loop");
                if (gameEndListener != null) {
                    gameEndListener.onGameEnd();
                    Log.d("SnakeGameController", "Calling game end listener");
                }
                post(this::showGameOverToast);
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawGame(Canvas canvas) {
        Log.d("SnakeGameController", "Drawing game");
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

        paint.setColor(Color.WHITE); // Set color for text
        paint.setTextSize(30); // Set text size
        canvas.drawText("Score: " + snakeGame.getScore(), 10, 30, paint);

        int defaultHighScore = 5; // You can change this value as needed
        canvas.drawText("High Score: " + defaultHighScore, 10, 60, paint);
    }
    public void restartGame() {
        Log.d("SnakeGameController", "Restarting game");
        playing = true;
        snakeGame.resetScore();
        snakeGame.restartGame();
        if (gameThread != null) {
            gameThread.interrupt();
        }
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Log.d("SnakeGameController", "Touch event: " + action);

        if (snakeGame.isGameOver()) {
            if (action == MotionEvent.ACTION_DOWN) {
                Log.d("SnakeGameController", "Screen touched after game over.");
                return true;
            } else if (action == MotionEvent.ACTION_UP) {
                Log.d("SnakeGameController", "Screen released after game over, attempting to restart.");
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
    private void showGameOverToast () {
        Toast.makeText(getContext(), "Game Over! Tap to restart.", Toast.LENGTH_LONG).show();
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
}

