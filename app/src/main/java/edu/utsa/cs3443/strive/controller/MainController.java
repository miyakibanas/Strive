package edu.utsa.cs3443.strive.controller;

import android.view.View;

import edu.utsa.cs3443.strive.MainActivity;
import edu.utsa.cs3443.strive.R;

public class MainController implements View.OnClickListener {
    private MainActivity mainActivity;

    public MainController (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    // TODO: add textView to each switch that when tapped, opens settingsActivity for that specific switch
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.switch1) {
            mainActivity.startSettingsActivity();
        } else if (v.getId() == R.id.switch2){
            mainActivity.startSettingsActivity();
        } else if (v.getId() == R.id.switch3){
            mainActivity.startSettingsActivity();
        }
    }
}
