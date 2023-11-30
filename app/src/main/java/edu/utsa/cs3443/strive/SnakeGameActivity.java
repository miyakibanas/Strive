/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * Activity responsible for managing the game's user interface, game state, and interactions between the user and the game.
 * It initializes the game, handling game state changes, managing user inputs, and transitioning back to the main activity upon certain conditions.
*/
package edu.utsa.cs3443.strive;

import static edu.utsa.cs3443.strive.AlarmSetupActivity.ALARM_REQUEST_CODE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.SnakeGameController;
import edu.utsa.cs3443.strive.model.AlarmReceiver;
import edu.utsa.cs3443.strive.model.SnakeGame;


public class SnakeGameActivity extends AppCompatActivity {
    private SnakeGameController snakeGameController;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        snakeGameController = findViewById(R.id.snakeGameController);
        snakeGameController.setMainActivity(this);
        snakeGameController.getSnakeGame().setScoreUpdateListener(new SnakeGame.ScoreUpdateListener() {
            @Override
            public void onDefaultScoreReached() {
                returnToMainActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        snakeGameController.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        snakeGameController.pause();
    }

    /**
     * Transitions from the Snake Game back to the main activity.
     * This method is typically called when a certain condition in the game is met,
     * like achieving a target score.
     */
    public void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
