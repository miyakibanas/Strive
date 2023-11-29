/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * MainActivity serves as the primary interface for the Strive Project's alarm system.
 * It displays a list of set alarms and provides the ability to add new alarms.
 * This activity utilizes a RecyclerView to show alarms and a FloatingActionButton
 * to add new alarms. It also handles the result of alarm setup activities and updates
 * the list of alarms accordingly.
 * The activity employs an ActivityResultLauncher to handle the result from AlarmSetupActivity.
 * When an alarm is added or modified, the alarm list is refreshed to reflect the changes.
*/
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

    /**
     * Initializes the activity, sets up the RecyclerView for displaying alarms,
     * and initializes the FloatingActionButton for adding new alarms.
     * It retrieves and displays the current list of alarms from the SettingsController.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
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

    /**
     * Handles click events on the FloatingActionButton. Launches AlarmSetupActivity
     * to allow the user to add a new alarm. Uses an ActivityResultLauncher to handle
     * the result from the AlarmSetupActivity.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AlarmSetupActivity.class);
        alarmSetupActivityResultLauncher.launch(intent);
    }

    /**
     * Refreshes the list of alarms displayed in the RecyclerView. It retrieves the latest
     * list of alarms from the SettingsController and notifies the adapter of any changes.
     *
     */
    private void refreshAlarmList() {
        alarms = settingsController.getAlarms();
        alarmAdapter.setAlarms(alarms);
        alarmAdapter.notifyDataSetChanged();
    }
}