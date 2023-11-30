package edu.utsa.cs3443.strive.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Question {

    private String question;
    private String answer;
    private String[] choices;

    /**
     * Constructor for Question objects
     *
     * @param question
     * @param answer
     * @param choices
     */
    public Question (String question, String answer, String[] choices){
        this.question = question;
        this.answer = answer;
        this.choices = choices;
    }

    /**
     * Grabs questions from csv, creates objects out of them, and returns a random one
     *
     * @param manager
     */
    public void loadQuestion(AssetManager manager){
        ArrayList<Question> questionList = new ArrayList<>();
        InputStream inputStream;
        Scanner scanner;
        try {
            inputStream = manager.open("questions.csv");
            scanner = new Scanner( inputStream );

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                String[] choices = {tokens[1], tokens[2], tokens[3]};

                Question question = new Question(tokens[0], tokens[1], choices);
                questionList.add(question);
            }
            inputStream.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!questionList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(questionList.size());
            Question randomQuestion = questionList.get(randomIndex);
            this.question = randomQuestion.getQuestion();
            this.answer = randomQuestion.getAnswer();
            this.choices = randomQuestion.getChoices();
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
