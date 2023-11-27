package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.ExerciseAlarmController;

public class ExerciseAlarm extends AppCompatActivity{

    ExerciseAlarmController alarmController;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.exercise_alarm);
        alarmController=new ExerciseAlarmController(this);

    }
}
