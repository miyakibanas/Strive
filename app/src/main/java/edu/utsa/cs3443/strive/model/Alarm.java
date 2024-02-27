/**
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * Alarm includes details such as the alarm time, label, repeat days, mission, sound, and snooze interval.
 * The class provides functionality to manage the state and properties of an alarm,
 * including enabling/disabling the alarm and setting various alarm attributes.
 * Each alarm has a unique identifier generated upon creation.
 * This model is crucial for managing alarm data and is used throughout the application
 * to schedule, display, and modify alarms.
*/
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

        repeatDays.put("Monday", false);
        repeatDays.put("Tuesday", false);
        repeatDays.put("Wednesday", false);
        repeatDays.put("Thursday", false);
        repeatDays.put("Friday", false);
        repeatDays.put("Saturday", false);
        repeatDays.put("Sunday", false);

        isEnabled = false;
        label = "";
        mission = "";
        sound = "";
        snoozeInterval = 0;
    }

    /**
     * Checks whether the alarm is enabled or not.
     *
     * @return
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Sets the enabled state of the alarm.
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     *
     * @return
     */
    public String getAlarmTime() {
        return alarmTime;
    }

    /**
     *
     * @param alarmTime
     */
    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     */
    public Map<String, Boolean> getRepeatDays() {
        return repeatDays;
    }

    /**
     *
     * @param repeatDays
     */
    public void setRepeatDays(Map<String, Boolean> repeatDays) {
        this.repeatDays = repeatDays;
    }

    /**
     *
     * @return
     */
    public String getMission() {
        return mission;
    }

    /**
     *
     * @param mission
     */
    public void setMission(String mission) {
        this.mission = mission;
    }

    /**
     *
     * @return
     */
    public static String getSound() {
        return sound;
    }

    /**
     *
     * @param sound
     */
    public void setSound(String sound) {
        this.sound = sound;
    }

    /**
     *
     * @return
     */
    public int getSnoozeInterval() {
        return snoozeInterval;
    }

    /**
     *
     * @param snoozeInterval
     */
    public void setSnoozeInterval(int snoozeInterval) {
        this.snoozeInterval = snoozeInterval;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Generates a unique identifier for the alarm using UUID.
     *
     * @return
     */
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}