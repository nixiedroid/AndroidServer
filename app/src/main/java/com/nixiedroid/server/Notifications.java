package com.nixiedroid.server;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

public class Notifications {
    final NotificationManager nm;
    String appname = "Server";
    String CHANNEL_ID  ="server";
    String CHANNEL_ID_QUIET  ="serverMuted";
    int icon = R.drawable.ic_notification;
   final Context context;

    public Notifications(Context context, NotificationManager nm) {
        this.context  = context;
        this.nm = nm;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationsChannel(CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
            createNotificationsChannel(CHANNEL_ID_QUIET,NotificationManager.IMPORTANCE_MIN);
        }
    }
    @SuppressWarnings("deprecated")
    public void postNotification(String message, boolean sound){
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification = new Notification.Builder(context,sound?CHANNEL_ID:CHANNEL_ID_QUIET)
                    .setContentTitle("Server")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_notification)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setContentTitle("Server")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setPriority(sound?Notification.PRIORITY_HIGH:Notification.PRIORITY_LOW)
                    .build();
        } else {
            notification = new Notification(icon, message, 0);
            notification.setLatestEventInfo(context, appname, message, null);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }

        nm.notify(0, notification);

    }
    private void createNotificationsChannel(String channelId, int importance){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
