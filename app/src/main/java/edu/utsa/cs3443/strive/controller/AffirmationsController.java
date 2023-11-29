// Luis Quintero
package edu.utsa.cs3443.strive.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import edu.utsa.cs3443.strive.MainActivity;

import edu.utsa.cs3443.strive.R;

/**
 * Controller for handling affirmations activity.
 */
public class AffirmationsController
{
    private Activity activity;
    private TextView alarmTimeTextView;
    private LinearLayout questionsLayout;
    private Button submitButton;
    private List<EditText> answerFields;
    private List<String> questions;

    /**
     * Constructor to initialize the affirmations controller.
     *
     * @param activity The activity where this controller is used.
     */
    public AffirmationsController(Activity activity)
    {
        this.activity = activity;
        this.questionsLayout = activity.findViewById(R.id.questionsLayout);
        this.alarmTimeTextView = activity.findViewById(R.id.alarmTimeTextView);
        this.submitButton = activity.findViewById(R.id.submitAnswersButton);
        this.answerFields = new ArrayList<>();
        this.questions = loadQuestionsFromCSV("affirmationsQ.csv");
        displayRandomQuestions(3);
        setUpSubmitButton();
        setCurrentTime();
    }

    /**
     * Sets the current time on the alarmTimeTextView.
     */
    private void setCurrentTime()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = format.format(calendar.getTime());
        alarmTimeTextView.setText(currentTime);
    }

    /**
     * Loads questions from a CSV file.
     *
     * @param fileName The name of the CSV file.
     * @return A list of questions.
     */
    private List<String> loadQuestionsFromCSV(String fileName)
    {
        List<String> questions = new ArrayList<>();
        try
        {
            InputStream inputStream = activity.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
            {
                questions.add(line);
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Displays a random set of questions pulled from the CSV file
     *
     * @param numberOfQuestions The number of questions to display.
     */
    private void displayRandomQuestions(int numberOfQuestions)
    {
        Collections.shuffle(questions, new Random());
        for (int i = 0; i < numberOfQuestions && i < questions.size(); i++)
        {
            addQuestionAnswerView(questions.get(i));
        }
    }

    /**
     * Adds a question and answer view
     *
     * @param question The question to be added.
     */
    private void addQuestionAnswerView(String question)
    {
        TextView questionTextView = new TextView(activity);
        EditText answerEditText = new EditText(activity);

        questionTextView.setText(question);
        questionTextView.setTextColor(Color.WHITE);
        questionTextView.setTextSize(18);

        answerEditText.setHint("Type your answer");
        answerEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        answerEditText.setTextColor(Color.WHITE);
        answerEditText.setHintTextColor(Color.GRAY);
        answerEditText.setGravity(Gravity.START);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        questionTextView.setLayoutParams(layoutParams);
        answerEditText.setLayoutParams(layoutParams);

        questionsLayout.addView(questionTextView);
        questionsLayout.addView(answerEditText);
        answerFields.add(answerEditText);
    }


    /**
     * Sets up the submit button functionality.
     */
    private void setUpSubmitButton()
    {
        submitButton.setOnClickListener(v ->
        {
            if (areAllQuestionsAnswered())
            {
                Toast.makeText(activity, "Have a great day!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            } else
            {
                Toast.makeText(activity, "Please answer all the questions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Checks if all questions have been answered.
     *
     * @return True if all questions are answered, false otherwise.
     */
    private boolean areAllQuestionsAnswered()
    {
        for (EditText answerField : answerFields)
        {
            if (answerField.getText().toString().trim().isEmpty())
            {
                return false;
            }
        }
        return true;
    }

}
