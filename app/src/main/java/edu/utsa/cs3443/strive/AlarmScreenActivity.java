package edu.utsa.cs3443.strive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.utsa.cs3443.strive.model.AlarmReceiver;
import edu.utsa.cs3443.strive.model.Alarm;

public class AlarmScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private Button snoozeButton;
    private Button startMissionButton;
    private TextView currentTimeTextView;
    private static final int SNOOZE_DURATION_MS = 5 * 60 * 1000;
    private void playSelectedSound(String soundChoice) {
        int soundResourceId = R.raw.sound1;

        if (soundChoice != null) {
            switch (soundChoice) {
                case "Sound1":
                    soundResourceId = R.raw.sound1;
                    break;
                case "Sound2":
                    soundResourceId = R.raw.sound2;
                    break;
                case "Sound3":
                    soundResourceId = R.raw.sound3;
                    break;
            }
        }
        try {
            mediaPlayer = MediaPlayer.create(this, soundResourceId); // replace with your actual sound file
            if (mediaPlayer == null) {
                Log.e("AlarmScreenActivity", "Failed to create MediaPlayer");

            } else {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        } catch (Exception e) {
            Log.e("AlarmScreenActivity", "Error creating MediaPlayer", e);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);

        snoozeButton = findViewById(R.id.snoozeButton);
        startMissionButton = findViewById(R.id.startMissionButton);
        currentTimeTextView = findViewById(R.id.textView_current_time);
        setCurrentTime(currentTimeTextView);
        String soundChoice = getIntent().getStringExtra("soundChoice");
        playSelectedSound(soundChoice);

        snoozeButton.setOnClickListener(this);
        startMissionButton.setOnClickListener(this);

        Log.d("AlarmScreen", "Alarm Screen Activity started.");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.snoozeButton) {
            snoozeAlarm();
        } else if (v.getId() == R.id.startMissionButton) {
            startMission();
        }
    }

    private void setCurrentTime(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = format.format(calendar.getTime());
        textView.setText(currentTime);
    }

    private void snoozeAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        long snoozeTime = System.currentTimeMillis() + SNOOZE_DURATION_MS;
        scheduleAlarm(snoozeTime);
        Toast.makeText(this, "Snooze for 5 minutes", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void scheduleAlarm(long triggerTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("soundChoice", Alarm.getSound());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
    private void startMission() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        String mission = getIntent().getStringExtra("mission");
        if ("Snake Game".equals(mission)) {
            Intent intent = new Intent(this, SnakeGameActivity.class);
            startActivity(intent);
        } else if ("Question".equals(mission)) {
            Intent intent = new Intent( this, QuestionActivity.class);
            startActivity(intent);
        }
        else if ("Affirmations".equals(mission)) {
            Intent intent = new Intent( this, QuestionActivity.class);
        }
        else {
            Toast.makeText(this, "Starting mission: " + mission, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}