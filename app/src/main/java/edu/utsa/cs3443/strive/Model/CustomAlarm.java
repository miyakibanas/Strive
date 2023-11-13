package edu.utsa.cs3443.strive.Model;

public class CustomAlarm extends Alarm {
    private String question;

    public CustomAlarm( int alarmTime, boolean alarmEnabled, String question ){
        super( alarmTime, alarmEnabled );
        this.question = question;
    }
}
