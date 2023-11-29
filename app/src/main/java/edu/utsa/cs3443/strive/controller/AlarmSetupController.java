package edu.utsa.cs3443.strive.controller;

import android.content.Context;

import edu.utsa.cs3443.strive.model.Alarm;

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
