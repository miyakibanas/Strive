package edu.utsa.cs3443.strive.controller;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.utsa.cs3443.strive.QuestionActivity;
import edu.utsa.cs3443.strive.model.Question;

public class QuestionController implements View.OnClickListener{
    QuestionActivity questionActivity;
    private Question question = new Question(null, null, null);
    private AssetManager manager;

    public QuestionController (QuestionActivity questionActivity, AssetManager manager) {
        this.questionActivity = questionActivity;
        this.manager = manager;
    }

    /**
     * Uses question loaded to display onto QuestionActivity
     */
    public void setUpQuestion() {
        question.loadQuestion( manager );
        questionActivity.displayQuestion(question.getQuestion(), question.getChoices());
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // Handle user's answer and show a Toast message indicating if they are correct or incorrect
        Button selectedButton = (Button) v;
        String selectedAnswer = selectedButton.getText().toString();
        String correctAnswer = question.getAnswer();

        if (correctAnswer != null && correctAnswer.contains(selectedAnswer)) {
            Toast.makeText(questionActivity, "Correct!", Toast.LENGTH_LONG).show();
            questionActivity.delayReturnToMainActivity();
        } else {
            Toast.makeText(questionActivity, "Incorrect. The correct answer is: " + correctAnswer, Toast.LENGTH_LONG).show();
        }

    }
}
