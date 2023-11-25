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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.alarmTime.setText(alarm.getAlarmTime());
        holder.alarmEnabled.setChecked(alarm.isEnabled());

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

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTime;
        Switch alarmEnabled;
        View removeButton;
        public ViewHolder(View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarm_time);
            alarmEnabled = itemView.findViewById(R.id.alarm_enabled);
            removeButton = itemView.findViewById(R.id.remove_alarm);
        }
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarmList = alarms;
        notifyDataSetChanged();
    }

}