package edu.utsa.cs3443.strive;

import static edu.utsa.cs3443.strive.AlarmSetupActivity.ALARM_REQUEST_CODE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.SnakeGameController;
import edu.utsa.cs3443.strive.model.AlarmReceiver;


public class SnakeGameActivity extends AppCompatActivity {
    private SnakeGameController snakeGameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        snakeGameController = findViewById(R.id.snakeGameController);
        snakeGameController.setGameEndListener(new SnakeGameController.GameEndListener() {
            @Override
            public void onGameEnd() {
                handleGameEnd();
            }
        });
    }
    private void handleGameEnd() {
        delayReturnToMainActivity();
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

    private void delayReturnToMainActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SnakeGameActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close current activity if needed
                    }
                }, 3000); // 3 seconds delay
            }
        });
    }
}
