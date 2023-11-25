package edu.utsa.cs3443.strive.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import edu.utsa.cs3443.strive.AlarmScreenActivity;
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarm received, starting AlarmScreenActivity");

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