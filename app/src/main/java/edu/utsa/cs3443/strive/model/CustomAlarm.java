package edu.utsa.cs3443.strive.model;

public class CustomAlarm extends Alarm {
    private String question;

    public CustomAlarm( int alarmTime, boolean alarmEnabled, String question ){
        super( alarmTime, alarmEnabled );
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
