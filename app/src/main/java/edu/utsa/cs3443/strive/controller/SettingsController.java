package edu.utsa.cs3443.strive.controller;

import edu.utsa.cs3443.strive.model.Alarm;
import edu.utsa.cs3443.strive.SettingsActivity;

public class SettingsController {
    private SettingsActivity activity;
    private Alarm alarm;

    public SettingsController(SettingsActivity activity) {
        this.activity = activity;
        this.alarm = new Alarm();
    }

    public void updateCurrentTime() {
        String ampm = "AM";
        int h, m, s;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        h = cal.get(java.util.Calendar.HOUR);
        m = cal.get(java.util.Calendar.MINUTE);
        s = cal.get(java.util.Calendar.SECOND);
        if (cal.get(java.util.Calendar.AM_PM) == java.util.Calendar.PM) {
            ampm = "PM";
        }
        h = (h == 0) ? 12 : h;
        String formattedTime = String.format("%02d:%02d:%02d %s", h, m, s, ampm);
        activity.setCurrentTime(formattedTime);

        if (alarm.getAlarmTime() != null && alarm.getAlarmTime().equals(formattedTime)) {
            activity.playRingtone();
        }

        activity.scheduleUpdateTime();
    }

    public void setAlarm() {
        if (alarm.isAlarmSet()) {
            alarm.setAlarmTime(null);
            activity.pauseRingtone();
            activity.showContent();
            activity.setButtonText("Set Alarm");
        } else {
            String hour = activity.getSelectedHour();
            String minute = activity.getSelectedMinute();
            String ampm = activity.getSelectedAMPM();
            if (hour.equals("Hour") || minute.equals("Minute") || ampm.equals("AM/PM")) {

            } else {
                alarm.setAlarmTime(hour + ":" + minute + " " + ampm);
                activity.hideContent();
                activity.setButtonText("Clear Alarm");
            }
        }
    }
}

