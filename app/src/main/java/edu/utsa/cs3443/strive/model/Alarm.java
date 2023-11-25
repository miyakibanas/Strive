package edu.utsa.cs3443.strive.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Alarm implements Serializable {
    private String alarmTime;
    private boolean isEnabled;
    private String label;
    private Map<String, Boolean> repeatDays;
    private String mission;
    private static String sound;
    private int snoozeInterval;
    private String id;

    public Alarm() {
        this.id = generateUniqueId();
        repeatDays = new HashMap<>();

        // Initialize all days to false
        repeatDays.put("Monday", false);
        repeatDays.put("Tuesday", false);
        repeatDays.put("Wednesday", false);
        repeatDays.put("Thursday", false);
        repeatDays.put("Friday", false);
        repeatDays.put("Saturday", false);
        repeatDays.put("Sunday", false);

        // Default values for other fields
        isEnabled = false;
        label = "";
        mission = "";
        sound = "";
        snoozeInterval = 0; // Default snooze interval
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, Boolean> getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(Map<String, Boolean> repeatDays) {
        this.repeatDays = repeatDays;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public static String getSound() {

        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public int getSnoozeInterval() {
        return snoozeInterval;
    }

    public void setSnoozeInterval(int snoozeInterval) {
        this.snoozeInterval = snoozeInterval;
    }

    public String getId() {
        return id;
    }

    private String generateUniqueId() {
        // Generate a unique ID, e.g., using UUID
        return UUID.randomUUID().toString();
    }
}