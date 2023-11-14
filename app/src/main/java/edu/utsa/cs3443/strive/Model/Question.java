package edu.utsa.cs3443.strive.Model;

public class Question {

    private String question;
    private String answer;
    private String[] choices;

    public Question (String question, String answer, String[] choices){
        this.question = question;
        this.answer = answer;
        this.choices = choices;
    }


}
