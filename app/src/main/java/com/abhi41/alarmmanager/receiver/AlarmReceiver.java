package com.abhi41.alarmmanager.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.abhi41.alarmmanager.R;

import static com.abhi41.alarmmanager.Util.App.CHANNEL_ID;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Notification notification = new NotificationCompat.Builder(context  ,CHANNEL_ID)
                .setContentTitle("Alarm")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(intent.getStringExtra("send_data"))
                .build();


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);



    }
}
