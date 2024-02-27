/**
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * AlarmSetupController is responsible for handling the setup and saving of alarms.
 * It works in conjunction with the SettingsController to manage the persistence of alarm data.
 * This controller simplifies the process of saving alarm details to the shared preferences.
 * It acts as an intermediary between the alarm setup view and the underlying data storage mechanism,
 * ensuring that the alarm data is correctly saved and managed.
*/
package edu.utsa.cs3443.strive.controller;

import android.content.Context;

import edu.utsa.cs3443.strive.model.Alarm;

public class AlarmSetupController {
    private SettingsController settingsController;
    private Context context;

    /**
     * Initializes the controller with the provided context.
     * Sets up the SettingsController to manage alarm data.
     *
     * @param context
     */
    public AlarmSetupController(Context context) {
        this.context = context;
        this.settingsController = new SettingsController(context);
    }

    /**
     * Saves the details of the provided alarm by adding it to the stored list of alarms
     * using the SettingsController.
     *
     * @param alarm
     */
    public void saveAlarmDetails(Alarm alarm) {
        settingsController.addAlarm(alarm);
    }

}
