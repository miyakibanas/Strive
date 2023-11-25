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

    public SettingsController(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
    }

    public void addAlarm(Alarm alarm) {
        List<Alarm> alarms = getAlarms();
        alarms.add(alarm);
        saveAlarms(alarms);
    }

    public List<Alarm> getAlarms() {
        String json = sharedPreferences.getString(ALARM_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Alarm>>() {}.getType();
        List<Alarm> alarms = gson.fromJson(json, type);
        return alarms == null ? new ArrayList<>() : alarms;
    }

    public void removeAlarm(Alarm alarm) {
        List<Alarm> alarms = getAlarms();
        alarms.removeIf(a -> a.getId().equals(alarm.getId()));
        saveAlarms(alarms);
    }

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

    public void saveAlarms(List<Alarm> alarms) {
        String json = gson.toJson(alarms);
        sharedPreferences.edit().putString(ALARM_LIST_KEY, json).apply();
    }

}

