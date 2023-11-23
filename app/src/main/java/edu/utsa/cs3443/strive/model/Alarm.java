package edu.utsa.cs3443.strive.model;

public class Alarm {
    private int alarmTime;
    private boolean alarmEnabled;

    public Alarm ( int alarmTime, boolean alarmEnabled ){
        this.alarmTime = alarmTime;
        this.alarmEnabled = alarmEnabled;
    }
    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
    public boolean isAlarmSet(){
        return alarmTime != null;
    }
}
