package edu.utsa.cs3443.strive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.utsa.cs3443.strive.controller.AlarmSetupController;
import edu.utsa.cs3443.strive.model.Alarm;
import edu.utsa.cs3443.strive.model.AlarmReceiver;

public class AlarmSetupActivity extends AppCompatActivity implements View.OnClickListener {

    private TimePicker timePicker;
    private CheckBox checkBoxMonday;
    private CheckBox checkBoxTuesday;
    private CheckBox checkBoxWednesday;
    private CheckBox checkBoxThursday;
    private CheckBox checkBoxFriday;
    private Spinner spinnerMission;
    private Spinner spinnerSound;
    private Spinner spinnerSnooze;
    private EditText editTextAlarmLabel;
    public static final int ALARM_REQUEST_CODE = 1;
    private AlarmSetupController alarmSetupController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setup);

        timePicker = findViewById(R.id.time_picker);
        checkBoxMonday = findViewById(R.id.checkBox_monday);
        checkBoxTuesday = findViewById(R.id.checkBox_tuesday);
        checkBoxWednesday = findViewById(R.id.checkBox_wednesday);
        checkBoxThursday = findViewById(R.id.checkBox_thursday);
        checkBoxFriday = findViewById(R.id.checkBox_friday);
        spinnerMission = findViewById(R.id.spinner_mission);
        spinnerSound = findViewById(R.id.spinner_sound);
        spinnerSnooze = findViewById(R.id.spinner_snooze);
        editTextAlarmLabel = findViewById(R.id.editText_alarm_label);

        alarmSetupController = new AlarmSetupController(this);

        findViewById(R.id.button_set_alarm).setOnClickListener(this);

        ArrayAdapter<CharSequence> soundAdapter = ArrayAdapter.createFromResource(this,
                R.array.sound_choices, android.R.layout.simple_spinner_item);
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSound.setAdapter(soundAdapter);

        ArrayAdapter<CharSequence> snoozeAdapter = ArrayAdapter.createFromResource(this,
                R.array.snooze_duration_choices, android.R.layout.simple_spinner_item);
        snoozeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSnooze.setAdapter(snoozeAdapter);

        String[] missionsArray = new String[]{
                "Snake Game", //add your mission here
                "Question"
        };
        ArrayAdapter<String> missionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, missionsArray);
        spinnerMission.setAdapter(missionAdapter);

    }

    @Override
    public void onClick(View v) {
        if (checkScheduleExactAlarmPermission()) {
            saveAlarm();
        }
    }

    private void saveAlarm() {
        Alarm alarm = new Alarm();

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String alarmTime = String.format("%02d:%02d", hour, minute);
        alarm.setAlarmTime(alarmTime);

        String label = editTextAlarmLabel.getText().toString();
        alarm.setLabel(label != null ? label : "");

        Map<String, Boolean> repeatDays = new HashMap<>();
        repeatDays.put("Monday", checkBoxMonday.isChecked());
        repeatDays.put("Tuesday", checkBoxTuesday.isChecked());
        repeatDays.put("Wednesday", checkBoxWednesday.isChecked());
        repeatDays.put("Thursday", checkBoxThursday.isChecked());
        repeatDays.put("Friday", checkBoxFriday.isChecked());
        alarm.setRepeatDays(repeatDays);

        Object missionItem = spinnerMission.getSelectedItem();
        String mission = missionItem != null ? missionItem.toString() : "";
        alarm.setMission(mission);

        String selectedSound = spinnerSound.getSelectedItem().toString();
        alarm.setSound(selectedSound);

        String selectedSnoozeDuration = spinnerSnooze.getSelectedItem().toString();

        int snoozeMinutes = convertDurationToMinutes(selectedSnoozeDuration);
        alarm.setSnoozeInterval(snoozeMinutes);

        alarmSetupController.saveAlarmDetails(alarm);

        scheduleExactAlarm(alarm);
    }

    private int convertDurationToMinutes(String durationText) {
        return Integer.parseInt(durationText.replaceAll("[^0-9]", ""));
    }

    private void scheduleExactAlarm(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("mission", alarm.getMission());
        intent.putExtra("soundChoice", alarm.getSound());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, alarm.getId().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.d("AlarmSetup", "Scheduling alarm at time: " + calendar.getTimeInMillis());

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


        Toast.makeText(this, "Alarm set for " + alarm.getAlarmTime(), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
    private boolean checkScheduleExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return false;
            }
        }
        return true;
    }
}