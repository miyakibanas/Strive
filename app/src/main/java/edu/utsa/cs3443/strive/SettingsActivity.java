package edu.utsa.cs3443.strive;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import edu.utsa.cs3443.strive.Controller.SettingsController;


public class SettingsActivity extends AppCompatActivity {
    private TextView currentTime;
    private Spinner[] selectMenu;
    private Button setAlarmBtn;
    private SettingsController settingsController;
    private MediaPlayer ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        currentTime = findViewById(R.id.h1);
        selectMenu = new Spinner[3];
        selectMenu[0] = findViewById(R.id.select1);
        selectMenu[1] = findViewById(R.id.select2);
        selectMenu[2] = findViewById(R.id.select3);
        setAlarmBtn = findViewById(R.id.button);
        ringtone = MediaPlayer.create(this, R.raw.ringtone);

        settingsController = new SettingsController(this);

        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsController.setAlarm();
            }
        });
    }

    public void setCurrentTime(String time) {
        currentTime.setText(time);
    }

    public String getSelectedHour() {
        return selectMenu[0].getSelectedItem().toString();
    }

    public String getSelectedMinute() {
        return selectMenu[1].getSelectedItem().toString();
    }

    public String getSelectedAMPM() {
        return selectMenu[2].getSelectedItem().toString();
    }

    public void playRingtone() {
        ringtone.start();
        ringtone.setLooping(true);
    }

    public void pauseRingtone() {
        ringtone.pause();
    }

    public void showContent() {
        findViewById(R.id.content).setVisibility(View.VISIBLE);
    }

    public void hideContent() {
        findViewById(R.id.content).setVisibility(View.INVISIBLE);
    }

    public void setButtonText(String text) {
        setAlarmBtn.setText(text);
    }

    public void scheduleUpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                settingsController.updateCurrentTime();
            }
        }, 1000);
    }
}