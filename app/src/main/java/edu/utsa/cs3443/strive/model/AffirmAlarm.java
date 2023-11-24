//Luis Quintero
//wqy692

package edu.utsa.cs3443.strive.model;

import java.util.ArrayList;
import java.util.List;

public class AffirmAlarm extends Alarm // !!!needs to be added to categories class!!!
{
    private List<String> affirmations;
    private boolean alarmActive;

    public AffirmAlarm()
    {        super();
        affirmations = new ArrayList<>();
        alarmActive = true;
    }

    // Method to add an affirmation to the list until there is 10
    public void addAffirmation(String affirmation)
    {
        if (affirmations.size() < 10)
        {
            affirmations.add(affirmation);
        }
        if (affirmations.size() == 10)
        {
            silenceAlarm(); // Silence the alarm after 10 affirmations
        }
    }

    // Method to check if the alarm can be silenced
    public boolean canSilenceAlarm()
    {
        return affirmations.size() >= 10;
    }

    // Method to silence the alarm
    private void silenceAlarm()
    {
        if (canSilenceAlarm())
        {
            alarmActive = false;
            // Code to actually stop the alarm goes here
        }
    }

    // Getter to check if the alarm is active
    public boolean isAlarmActive()
    {
        return alarmActive;
    }

    // Method to clear all affirmations
    public void clearAffirmations()
    {
        affirmations.clear();
        alarmActive = true; // Reactivate the alarm when affirmations are cleared
    }

    // Getter for all affirmations
    public List<String> getAffirmations()
    {
        return new ArrayList<>(affirmations); // Return a copy to prevent external modification
    }
}