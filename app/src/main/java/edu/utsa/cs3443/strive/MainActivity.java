package edu.utsa.cs3443.strive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;

import edu.utsa.cs3443.strive.controller.MainController;

public class MainActivity extends AppCompatActivity {
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        MainController mainController = new MainController(this);

        switch1.setOnClickListener(mainController);
        switch2.setOnClickListener(mainController);
        switch3.setOnClickListener(mainController);
    }

    public void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}