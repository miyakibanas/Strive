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

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.d("AlarmCancellation", "Alarm cancelled successfully.");
        } else {
            Log.e("AlarmCancellation", "AlarmManager is null, cannot cancel alarm.");
        }
    }

    public void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
