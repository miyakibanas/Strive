/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * SnakeGame manages the logic and it handles the snake's movement, collision detection, apple generation,
 * and score tracking. The snake grows when it eats apples and the game ends when the snake collides with
 * itself or the boundary of the grid.
 * The game keeps track of the player's score and notifies when a predefined high score is reached.
 * It also allows for restarting and updating the game state, including the direction of the snake.
*/

package edu.utsa.cs3443.strive.model;

import java.util.ArrayList;
import java.util.Random;

public class SnakeGame {
    public static final int WIDTH = 49;
    public static final int HEIGHT = 100;
    public int score;
    private ArrayList<Point> snake;
    private Point apple;
    private Random random = new Random();
    private boolean gameOver = false;
    private Direction direction = Direction.RIGHT;
    public static final int DEFAULT_HIGH_SCORE = 5;

    public SnakeGame() {
        resetGame();
        resetScore();
    }

    public interface ScoreUpdateListener {
        void onDefaultScoreReached();
    }

    private ScoreUpdateListener scoreUpdateListener;

    public void setScoreUpdateListener(ScoreUpdateListener listener) {
        this.scoreUpdateListener = listener;
    }

    /**
     * Updates the game state by moving the snake and checking for collisions or apple consumption.
     * Increases the score when an apple is eaten and checks if the game should end based on
     * the snake's collision with the boundary or itself.
     */
    public void updateGame() {
        try {
            if (!gameOver) {
                moveSnake();
                checkCollision();

                if (checkIfSnakeEatsApple()) {
                    score++;
                    generateApple();

                    if (hasReachedHighScore()) {
                        gameOver = true;
                    }
                }
            }
        } catch (Exception e) {
            gameOver = true;
        }
    }

    private boolean hasReachedHighScore() {
        return score >= DEFAULT_HIGH_SCORE;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    private void resetGame() {
        if (snake == null) {
            snake = new ArrayList<>();
        } else {
            snake.clear();
        }
        snake.add(new Point(0, 0));
        generateApple();
        gameOver = false;
        direction = Direction.RIGHT;
    }

    public void restartGame() {
        resetGame();
    }

    private void moveSnake() {
        Point newHead = getNextHead();
        snake.add(0, newHead);

        if (newHead.equals(apple)) {
            score++;
            generateApple();

            if (score >= DEFAULT_HIGH_SCORE) {
                if (scoreUpdateListener != null) {
                    scoreUpdateListener.onDefaultScoreReached();
                }
            }
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private Point getNextHead() {
        Point head = snake.get(0);
        switch (direction) {
            case UP:
                return new Point(head.getX(), head.getY() - 1);
            case DOWN:
                return new Point(head.getX(), head.getY() + 1);
            case LEFT:
                return new Point(head.getX() - 1, head.getY());
            case RIGHT:
                return new Point(head.getX() + 1, head.getY());
            default:
                return head;
        }
    }

    private void checkCollision() {
        Point head = snake.get(0);

        if (head.getX() < 0 || head.getX() >= WIDTH || head.getY() < 0 || head.getY() >= HEIGHT) {
            gameOver = true;
            return;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                return;
            }
        }
    }

    private boolean checkIfSnakeEatsApple() {
        Point head = snake.get(0);
        return head.equals(apple);
    }

    private void generateApple() {
        int x, y;
        do {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
            apple = new Point(x, y);
        } while (snake.contains(apple));
    }

    public void setDirection(Direction newDirection) {
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
                (direction == Direction.DOWN && newDirection != Direction.UP) ||
                (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
                (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection;
        }
    }

    public ArrayList<Point> getSnake() {
        return snake;
    }

    public Point getApple() {
        return apple;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Represents a point on the game grid. Used to track the position of the snake and apple.
     */
    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        /**
         * Compares this point with another object for equality.
         * Two points are equal if they have the same x and y coordinates.
         *
         * @param obj The object to compare with.
         * @return True if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        /**
         * Generates a hash code for this point.
         * The hash code is calculated based on the point's x and y coordinates.
         *
         * @return The hash code for this point.
         */
        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}