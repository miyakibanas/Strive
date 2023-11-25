package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.AlarmSetupController;
import edu.utsa.cs3443.strive.controller.SnakeGameController;


public class SnakeGameActivity extends AppCompatActivity {
    private SnakeGameController snakeGameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        snakeGameController = new SnakeGameController(this);
        snakeGameController.setGameEndListener(this::onGameEnd);

        Log.d("SnakeGame", "Snake Game Activity started.");
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
    private void onGameEnd() {
        Log.d("SnakeGameActivity", "Game end callback received");
        cancelAlarm();
        navigateToMainActivity();
    }

    private void cancelAlarm() {
        AlarmSetupController alarmSetupController = new AlarmSetupController(this);
        alarmSetupController.cancelAlarm();
        Log.d("SnakeGame", "Cancelling alarm.");
    }

    private void navigateToMainActivity() {
        Log.d("SnakeGameActivity", "Navigating to MainActivity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}