/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * SettingsController is responsible for managing the storage and retrieval of alarm settings.
 * It uses SharedPreferences to persistently store alarm data, allowing alarms to be saved, retrieved, updated,
 * and deleted.
 * This class is essential for handling the data persistence of alarms, ensuring that alarm settings are maintained
 * across app sessions.
*/
package edu.utsa.cs3443.strive.controller;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3443.strive.model.Alarm;

public class SettingsController {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private final String ALARM_LIST_KEY = "alarms";

    /**
     * Initializes the controller with the provided context and sets up SharedPreferences for data storage.
     *
     * @param context The context used to access SharedPreferences.
     */
    public SettingsController(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
    }

    /**
     * Adds a new alarm to the stored list of alarms.
     *
     * @param alarm The alarm to add.
     */
    public void addAlarm(Alarm alarm) {
        List<Alarm> alarms = getAlarms();
        alarms.add(alarm);
        saveAlarms(alarms);
    }

    /**
     * Retrieves the list of stored alarms.
     *
     * @return A List of Alarm objects.
     */
    public List<Alarm> getAlarms() {
        String json = sharedPreferences.getString(ALARM_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Alarm>>() {}.getType();
        List<Alarm> alarms = gson.fromJson(json, type);
        return alarms == null ? new ArrayList<>() : alarms;
    }

    /**
     * Removes a specified alarm from the stored list of alarms.
     *
     * @param alarm The alarm to remove.
     */
    public void removeAlarm(Alarm alarm) {
        List<Alarm> alarms = getAlarms();
        alarms.removeIf(a -> a.getId().equals(alarm.getId()));
        saveAlarms(alarms);
    }

    /**
     * Updates an existing alarm in the stored list of alarms.
     *
     * @param updatedAlarm The alarm with updated information.
     */
    public void updateAlarm(Alarm updatedAlarm) {
        List<Alarm> alarms = getAlarms();
        for (int i = 0; i < alarms.size(); i++) {
            if (alarms.get(i).getId().equals(updatedAlarm.getId())) {
                alarms.set(i, updatedAlarm);
                break;
            }
        }
        saveAlarms(alarms);
    }

    /**
     * Saves the provided list of alarms to SharedPreferences.
     *
     * @param alarms The list of alarms to save.
     */
    public void saveAlarms(List<Alarm> alarms) {
        String json = gson.toJson(alarms);
        sharedPreferences.edit().putString(ALARM_LIST_KEY, json).apply();
    }

}

