package edu.utsa.cs3443.strive.controller;

import static edu.utsa.cs3443.strive.AlarmSetupActivity.ALARM_REQUEST_CODE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import edu.utsa.cs3443.strive.model.Alarm;
import edu.utsa.cs3443.strive.model.AlarmReceiver;

public class AlarmSetupController {
    private SettingsController settingsController;
    private Context context;

    public AlarmSetupController(Context context) {
        this.context = context;
        this.settingsController = new SettingsController(context);
    }

    public void saveAlarmDetails(Alarm alarm) {
        settingsController.addAlarm(alarm);
    }
    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
