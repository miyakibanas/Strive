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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze_countdown);

        countdownTextView = findViewById(R.id.countdownTextView);

        countDownTimer = new CountDownTimer(5 * 60 * 1000, 1000) { // Adjust this duration for your actual needs
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                countdownTextView.setText("Time Remaining: \n" + timeFormatted);
            }

            public void onFinish() {
                Intent intent = new Intent(SnoozeCountdownActivity.this, AlarmScreenActivity.class);
                intent.putExtra("isReturningFromSnooze", true);
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
