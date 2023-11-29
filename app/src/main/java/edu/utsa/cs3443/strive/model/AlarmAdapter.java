/**
 * @author Miyaki Banas (xqe858)
 * UTSA CS 3443 - Strive Project
 * FALL 2023
 *
 * AlarmAdapter is a custom adapter for RecyclerView that displays a list of alarms.
 * It handles the layout and binding of alarm data to views in the RecyclerView.
 * This adapter is responsible for managing the display of each alarm item, including
 * the time, enable/disable switch, and a button to remove the alarm.
 * It interacts with a SettingsController to update the alarm settings when the user
 * changes them.
*/

package edu.utsa.cs3443.strive.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.utsa.cs3443.strive.R;
import edu.utsa.cs3443.strive.controller.SettingsController;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private List<Alarm> alarmList;
    private SettingsController settingsController;

    public AlarmAdapter(List<Alarm> alarmList, SettingsController settingsController) {
        this.alarmList = alarmList;
        this.settingsController = settingsController;
    }

    /**
     * Creates new views (invoked by the layout manager) for each item in the RecyclerView.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager).
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.alarmTime.setText(alarm.getAlarmTime());
        holder.alarmEnabled.setChecked(alarm.isEnabled());
        holder.alarmLabelTextView.setText(alarm.getLabel());

        holder.alarmEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarm.setEnabled(isChecked);
            settingsController.updateAlarm(alarm);
        });

        holder.removeButton.setOnClickListener(v -> {
            alarmList.remove(position);
            notifyItemRemoved(position);
            settingsController.saveAlarms(alarmList);
        });
    }

    /**
     * Returns the size of the dataset (invoked by the layout manager).
     *
     * @return The number of items in the adapter's data set.
     */
    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    /**
     * ViewHolder describes an item view and metadata about its place within the RecyclerView.
     * It holds the views for each alarm item, including the time, enable/disable switch,
     * and a button to remove the alarm.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTime;
        TextView alarmLabelTextView;
        Switch alarmEnabled;
        View removeButton;
        public ViewHolder(View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarm_time);
            alarmEnabled = itemView.findViewById(R.id.alarm_enabled);
            removeButton = itemView.findViewById(R.id.remove_alarm);
            alarmLabelTextView = itemView.findViewById(R.id.alarm_label);
        }
    }

    /**
     * Updates the alarm list in the adapter and notifies that the data set has changed.
     *
     * @param alarms The new list of alarms to be displayed.
     */
    public void setAlarms(List<Alarm> alarms) {
        this.alarmList = alarms;
        notifyDataSetChanged();
    }

}