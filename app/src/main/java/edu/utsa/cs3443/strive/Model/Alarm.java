package edu.utsa.cs3443.strive.Model;

public abstract class Alarm {
    private int alarmTime;
    private boolean alarmEnabled;

    public Alarm ( int alarmTime, boolean alarmEnabled ) {
        this.alarmTime = alarmTime;
        this.alarmEnabled = alarmEnabled;
    }

    public int getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }
}
