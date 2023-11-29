package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import edu.utsa.cs3443.strive.controller.QuestionController;

public class QuestionActivity extends AppCompatActivity {
    AssetManager manager;
    private TextView questionText;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        manager = getAssets();
        questionText = findViewById(R.id.questionText);
        answerButton1 = findViewById(R.id.answer1Button);
        answerButton2 = findViewById(R.id.answer2Button);
        answerButton3 = findViewById(R.id.answer3Button);

        QuestionController questionController = new QuestionController(this, manager);

        answerButton1.setOnClickListener(questionController);
        answerButton2.setOnClickListener(questionController);
        answerButton3.setOnClickListener(questionController);

        questionController.setUpQuestion();
    }

    /**
     * Displays the question and answer choices on the buttons
     * @param question
     * @param answers
     */
    public void displayQuestion(String question, String[] answers){
        questionText.setText(question);

        // Randomize order of answer choices
        Random random = new Random();
        int firstNumber = random.nextInt(3);
        int secondNumber = random.nextInt(3);

        while (secondNumber == firstNumber) {
            secondNumber = random.nextInt(3);
        }

        int[] usedNumbers = {firstNumber, secondNumber};
        int thirdNumber = random.nextInt(3);
        while (thirdNumber == usedNumbers[0] || thirdNumber == usedNumbers[1]) {
            thirdNumber = random.nextInt(3);
        }
        answerButton1.setText(answers[firstNumber]);
        answerButton2.setText(answers[secondNumber]);
        answerButton3.setText(answers[thirdNumber]);
    }

    public void delayReturnToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close current activity if needed
            }
        }, 3000); // 3 seconds delay
    }
}
