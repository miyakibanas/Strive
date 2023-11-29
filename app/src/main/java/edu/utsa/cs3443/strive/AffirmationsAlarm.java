// Luis Quintero
package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.strive.controller.AffirmationsController;

/**
 * Activity for  affirmations alarm functionality.
 */
public class AffirmationsAlarm extends AppCompatActivity
{
    // Controller for the affirmations alarm.
    AffirmationsController alarmController;

    /**
     * Starts the affirmations activity.
     * @param savedInstanceState State of the application in a previous configuration.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmations);
        alarmController=new AffirmationsController(this);


    }
}
