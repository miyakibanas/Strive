package edu.utsa.cs3443.strive.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import edu.utsa.cs3443.strive.AlarmSetupActivity;
import edu.utsa.cs3443.strive.ExerciseAlarm;
import edu.utsa.cs3443.strive.MainActivity;
import edu.utsa.cs3443.strive.R;

public class ExerciseAlarmController {
    ExerciseAlarm exerciseAlarm;
    private String lastSelectedExercise;
    NumberPicker numberPicker;
    TextView exercise_name;
    ImageView img,backArrow,nextArrow;
    Button btnSave;
    Button btnPreview;
    public ExerciseAlarmController(ExerciseAlarm exerciseAlarm) {
        this.exerciseAlarm = exerciseAlarm;
        numberPicker=exerciseAlarm.findViewById(R.id.number_picker);
        exercise_name=exerciseAlarm.findViewById(R.id.exercise_name);
        backArrow=exerciseAlarm.findViewById(R.id.back_arrow);
        btnPreview=exerciseAlarm.findViewById(R.id.btnPreview);
        btnSave=exerciseAlarm.findViewById(R.id.btnSave);
        nextArrow=exerciseAlarm.findViewById(R.id.next_arrow);
        img=exerciseAlarm.findViewById(R.id.img);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        lastSelectedExercise = "Squat";
        img.setImageResource(R.drawable.squatting);
        exercise_name.setText(lastSelectedExercise);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchExercise();
            }
        });

        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchExercise();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exerciseAlarm, MainActivity.class);
                exerciseAlarm.startActivity(intent);
                exerciseAlarm.finish();
            }
        });
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exerciseAlarm, MainActivity.class);
                exerciseAlarm.startActivity(intent);
                exerciseAlarm.finish();
            }
        });
    }
    private void switchExercise() {
        if (lastSelectedExercise.equals("Squat")) {
            img.setImageResource(R.drawable.jumpingjacks);
            exercise_name.setText("Jumping Jacks");
            lastSelectedExercise = "Jumping Jacks";
        } else {
            img.setImageResource(R.drawable.squatting);
            exercise_name.setText("Squat");
            lastSelectedExercise = "Squat";
        }
    }



}
