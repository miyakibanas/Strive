/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * AlarmSetupActivity provides options to set the time, repeat days, mission, sound choice, snooze duration,
 * and an optional label for each alarm. The activity uses a TimePicker for time selection,
 * CheckBoxes for selecting repeat days, Spinners for selecting the mission, sound, and snooze duration,
 * and an EditText for the alarm label. Alarms are scheduled using the AlarmManager.
*/
package edu.utsa.cs3443.strive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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

    /**
     * Initializes the activity, sets up the UI elements, and initializes the AlarmSetupController.
     * This method also sets up the adapters for the spinners and onClick listeners for the buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
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

        ArrayAdapter<CharSequence> soundAdapter = ArrayAdapter.createFromResource(this, R.array.sound_choices, android.R.layout.simple_spinner_item);
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSound.setAdapter(soundAdapter);

        ArrayAdapter<CharSequence> snoozeAdapter = ArrayAdapter.createFromResource(this, R.array.snooze_duration_choices, android.R.layout.simple_spinner_item);
        snoozeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSnooze.setAdapter(snoozeAdapter);

        String[] missionsArray = new String[]{
                "Snake Game", //add your mission here
                "Question",
                "Affirmations",
                "Exercise"
        };
        ArrayAdapter<String> missionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, missionsArray);
        spinnerMission.setAdapter(missionAdapter);

    }

    /**
     * Handles click events for the set alarm button.
     * It checks for the required alarm permissions and proceeds to save the alarm.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (checkScheduleExactAlarmPermission()) {
            saveAlarm();
        }
    }

    /**
     * Saves the alarm details entered by the user.
     * It creates a new Alarm object, sets its properties based on the user's input,
     * and schedules the alarm using the AlarmManager.
     */
    private void saveAlarm() {
        Alarm alarm = new Alarm();

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String alarmTime = String.format("%02d:%02d", hour, minute);
        alarm.setAlarmTime(alarmTime);

        String label = editTextAlarmLabel.getText().toString();
        alarm.setLabel(label);

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

    /**
     *
     * @param durationText The duration string to be converted.
     * @return The duration in minutes as an integer.
     */
    private int convertDurationToMinutes(String durationText) {
        return Integer.parseInt(durationText.replaceAll("[^0-9]", ""));
    }

    /**
     * Schedules the alarm to trigger at the specified time using the AlarmManager.
     * It creates a PendingIntent that triggers AlarmReceiver when the alarm time is reached.
     *
     * @param alarm The alarm to be scheduled.
     */
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

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Alarm set for " + alarm.getAlarmTime(), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    /**
     * Checks if the app has permission to schedule exact alarms.
     * If permission is not granted, it prompts the user to grant it.
     *
     * @return True if the permission is granted, false otherwise.
     */
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