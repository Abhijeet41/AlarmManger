package com.abhi41.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.abhi41.alarmmanager.databinding.ActivityMainBinding;
import com.abhi41.alarmmanager.receiver.AlarmReceiver;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TimePickerDialog timePicker;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btnOpenTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        final Calendar cldr = Calendar.getInstance();
                        cldr.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cldr.set(Calendar.MINUTE, minute);
                        cldr.set(Calendar.SECOND, 0);

                        updateTimeText(cldr);
                        startAlarm(cldr);
                    }
                }, hour, minutes, false);
                timePicker.show();
            }
        });

        binding.btnCloseAlarmPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleAlarm();
            }
        });
    }

    private void updateTimeText(Calendar cldr) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(cldr.getTime());
        binding.textView.setText(timeText);
    }

    private void startAlarm(Calendar cldr) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("send_data","Alarm services get started");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cldr.getTimeInMillis(), pendingIntent);

    }

    private void cancleAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);

    }
}