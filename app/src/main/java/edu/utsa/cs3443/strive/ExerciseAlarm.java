package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.ExerciseAlarmController;

/**
 * Activity for Exercise alarm functionality.
 */
public class ExerciseAlarm extends AppCompatActivity{

    ExerciseAlarmController alarmController;

    /**
     * Starts the exercise activity.
     * @param savedInstanceState State of the application in a previous configuration.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.exercise_alarm);
        alarmController=new ExerciseAlarmController(this);

    }
}
