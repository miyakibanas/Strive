package edu.utsa.cs3443.strive.Model;

public class Alarm {
    private String alarmTime;

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
