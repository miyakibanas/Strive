package edu.utsa.cs3443.strive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import edu.utsa.cs3443.strive.controller.SettingsController;
import edu.utsa.cs3443.strive.model.Alarm;
import edu.utsa.cs3443.strive.model.AlarmAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private List<Alarm> alarms;
    private SettingsController settingsController;
    private static final int REQUEST_CODE_ADD_ALARM = 1;
    private final ActivityResultLauncher<Intent> alarmSetupActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                refreshAlarmList();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsController = new SettingsController(this);

        recyclerView = findViewById(R.id.recycler_view_alarms);
        alarms = settingsController.getAlarms();
        alarmAdapter = new AlarmAdapter(alarms, settingsController);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(alarmAdapter);

        FloatingActionButton fabAddAlarm = findViewById(R.id.fab_add_alarm);
        fabAddAlarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AlarmSetupActivity.class);
        alarmSetupActivityResultLauncher.launch(intent);
    }

    private void refreshAlarmList() {
        alarms = settingsController.getAlarms();
        alarmAdapter.setAlarms(alarms);
        alarmAdapter.notifyDataSetChanged();
    }
}