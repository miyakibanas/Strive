/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * Activity responsible for displaying the snooze count down.
*/

package edu.utsa.cs3443.strive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

public class SnoozeCountdownActivity extends AppCompatActivity {
    private TextView countdownTextView;
    private CountDownTimer countDownTimer;

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
        setContentView(R.layout.activity_snooze_countdown);

        countdownTextView = findViewById(R.id.countdownTextView);

        countDownTimer = new CountDownTimer(5 * 60 * 1000, 1000) { 
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                countdownTextView.setText("Time Remaining: \n" + timeFormatted);
            }

            public void onFinish() {
                Intent intent = new Intent(SnoozeCountdownActivity.this, AlarmScreenActivity.class);
                intent.putExtra("isReturningFromSnooze", true);

                String mission = getIntent().getStringExtra("mission");
                if (mission != null) {
                    intent.putExtra("mission", mission);
                }

                String soundChoice = getIntent().getStringExtra("soundChoice");
                if (soundChoice != null) {
                    intent.putExtra("soundChoice", soundChoice);
                }

                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
