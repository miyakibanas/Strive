/**
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * AlarmScreenActivity displays the current time and provides options for snoozing the alarm or starting a mission.
 * It also plays the selected alarm sound and handles snooze and mission start functionalities.
 * The activity plays a sound based on the user's choice, allows snoozing the alarm,
 * and initiates different missions based on the user's selection.
 * It integrates a MediaPlayer to manage alarm sounds.
*/

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

    /**
     * Plays the selected alarm sound based on the user's choice.
     * The method initializes and configures a MediaPlayer instance to play the specified sound.
     *
     * @param soundChoice
     *
     */
    private void playSelectedSound(String soundChoice) {
        int soundResourceId = R.raw.sound1;

        if (soundChoice != null) {
            switch (soundChoice) {
                case "sound1":
                    soundResourceId = R.raw.sound1;
                    break;
                case "sound2":
                    soundResourceId = R.raw.sound2;
                    break;
                case "sound3":
                    soundResourceId = R.raw.sound3;
                    break;
            }
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        if (mediaPlayer == null) {
            Log.e("AlarmScreenActivity", "Failed to create MediaPlayer");
        } else {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }

    /**
     * Initializes the activity, setting up the UI components and configuring the MediaPlayer.
     * It retrieves the selected sound choice and mission from the intent and prepares the alarm screen.
     *
     * @param
     */
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

        boolean isReturningFromSnooze = getIntent().getBooleanExtra("isReturningFromSnooze", false);
        if (isReturningFromSnooze) {
            rescheduleAlarm();
        }

    }

    /**
     * Handles click events for the snooze and start mission buttons.
     * It triggers the snoozeAlarm or startMission methods based on the button clicked.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.snoozeButton) {
            snoozeAlarm();
        } else if (v.getId() == R.id.startMissionButton) {
            startMission();
        }
    }

    /**
     * Sets the current time on the specified TextView.
     * @param textView
     */
    private void setCurrentTime(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = format.format(calendar.getTime());
        textView.setText(currentTime);
    }

    /**
     * Initiates the snooze functionality of the alarm.
     * It pauses the MediaPlayer and launches the SnoozeCountdownActivity.
     *
     */
    private void snoozeAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }

        Intent snoozeIntent = new Intent(this, SnoozeCountdownActivity.class);
        snoozeIntent.putExtra("mission", getIntent().getStringExtra("mission"));
        snoozeIntent.putExtra("soundChoice", getIntent().getStringExtra("soundChoice"));
        startActivity(snoozeIntent);
        finish();
    }

    /**
     * Reschedules the alarm to go off after a predefined snooze duration.
     *
     */
    private void rescheduleAlarm() {
        long alarmTime = System.currentTimeMillis() + SNOOZE_DURATION_MS;

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("soundChoice", Alarm.getSound());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        }
    }

    /**
     * Starts the selected mission activity based on the user's choice.
     *
     */
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
            Intent intent = new Intent( this, AffirmationsAlarm.class);
            startActivity(intent);
        }
        else if ("Exercise".equals(mission)){
            Intent intent = new Intent(this, ExerciseAlarm.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Starting mission: " + mission, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    /**
     * Releases resources used by MediaPlayer upon the destruction of the activity.
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}