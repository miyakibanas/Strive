// Luis Quintero
package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.AffirmationsController;

public class AffirmationsAlarm extends AppCompatActivity
{
    AffirmationsController alarmController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmations);
        alarmController=new AffirmationsController(this);


    }
}
