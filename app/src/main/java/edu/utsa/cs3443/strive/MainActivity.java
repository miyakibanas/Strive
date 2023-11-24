package edu.utsa.cs3443.strive;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import edu.utsa.cs3443.strive.controller.MainController;

public class MainActivity extends AppCompatActivity {
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private TextView switch1Text;
    private TextView switch2Text;
    private TextView switch3Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainController mainController = new MainController(this);

        // Get id for three switches and their adjacent TextView
        switch1 = findViewById(R.id.switch1);
        switch1Text = findViewById(R.id.switch1Text);

        switch2 = findViewById(R.id.switch2);
        switch2Text = findViewById(R.id.switch2Text);

        switch3 = findViewById(R.id.switch3);
        switch3Text = findViewById(R.id.switch3Text);

        // Connect switches and TextViews
        switch1.setOnClickListener(view -> mainController.onSwitchClicked(1));
        switch1Text.setOnClickListener(view -> mainController.onSwitchTextClicked(1));

        switch2.setOnClickListener(view -> mainController.onSwitchClicked(2));
        switch2Text.setOnClickListener(view -> mainController.onSwitchTextClicked(2));

        switch3.setOnClickListener(view -> mainController.onSwitchClicked(3));
        switch3Text.setOnClickListener(view -> mainController.onSwitchTextClicked(3));
    }

}