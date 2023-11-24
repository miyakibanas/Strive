package edu.utsa.cs3443.strive.controller;

import android.content.Intent;
import android.view.View;

import edu.utsa.cs3443.strive.MainActivity;
import edu.utsa.cs3443.strive.R;
import edu.utsa.cs3443.strive.SettingsActivity;

public class MainController {
    private MainActivity mainActivity;

    public MainController (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void onSwitchClicked(int switchNumber) {
        // Toggle switch logic

    }

    public void onSwitchTextClicked(int switchNumber) {
        // Open SettingsActivity for the specific switch
        Intent intent = new Intent(mainActivity, SettingsActivity.class);
        intent.putExtra("SWITCH_NUMBER", switchNumber);
        mainActivity.startActivity(intent);
    }
}
