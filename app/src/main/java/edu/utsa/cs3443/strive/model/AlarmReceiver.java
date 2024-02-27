/**
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * AlarmReceiver is a BroadcastReceiver that responds to alarm events.
 * When an alarm is triggered, it starts the AlarmScreenActivity, passing along any relevant data
 * such as the selected sound choice and the mission. This receiver is a crucial component of the
 * alarm functionality in the Strive Project, ensuring that alarms activate the appropriate UI and behavior.
 * It extracts the extras from the incoming intent (like 'soundChoice' and 'mission') and forwards
 * them to the AlarmScreenActivity to handle the alarm event appropriately.
*/
package edu.utsa.cs3443.strive.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.utsa.cs3443.strive.AlarmScreenActivity;
public class AlarmReceiver extends BroadcastReceiver {
    /**
     * Called when the BroadcastReceiver is receiving an Intent broadcast.
     * Initiates the alarm screen with the necessary information about the alarm that was set.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmScreenIntent = new Intent(context, AlarmScreenActivity.class);
        alarmScreenIntent.putExtra("soundChoice", intent.getStringExtra("soundChoice"));

        String mission = intent.getStringExtra("mission");
        if (mission != null) {
            alarmScreenIntent.putExtra("mission", mission);
        }

        alarmScreenIntent.putExtras(intent.getExtras());
        alarmScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(alarmScreenIntent);
    }
}