package edu.utsa.cs3443.strive.model;

public class Alarm {
    private int alarmTime;
    private boolean alarmEnabled;

    public Alarm ( ){
        this.alarmTime = alarmTime;
        this.alarmEnabled = alarmEnabled;
    }

    public int getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }
    public boolean isAlarmSet(){
        return alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }
}
