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

}
