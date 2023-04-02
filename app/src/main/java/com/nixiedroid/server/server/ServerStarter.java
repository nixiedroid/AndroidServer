package com.nixiedroid.server.server;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import android.widget.Toast;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.server.MainActivity;
import com.nixiedroid.server.R;
import com.nixiedroid.settings.LogLevel;
import com.nixiedroid.settings.ServerSettingsStub;

public class ServerStarter extends Service {
    static NotificationManager nm;
    static String appname = "Server";
    static String CHANNEL_ID  ="server";
    static String CHANNEL_ID_QUIET  ="serverMuted";
    static int icon = R.drawable.ic_notification;

    boolean isRunning = false;
    public static final ServerSettingsStub settings = new ServerSettingsStub(new AndroidSettings());

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationsChannel(CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
        createNotificationsChannel(CHANNEL_ID_QUIET,NotificationManager.IMPORTANCE_MIN);
        }
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        ConfigStub stub = new ConfigStub(new SecretConfig());
        Program.setConfig(stub,settings);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        postNotification("Hello!",false);
        if (MainActivity.isAlive()){
            Program.settings().setLevel(LogLevel.INFO);
        } else {
            Program.settings().setLevel(LogLevel.NONE);
        }
        if ((flags & START_FLAG_RETRY) == 0) {
            if (!isRunning){
                Toast.makeText(this,"Server started",Toast.LENGTH_SHORT).show();
                Program.start();
                isRunning = true;
            } else {
                Toast.makeText(this,"Server is already started",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            postNotification("Hello! START",false);
            Toast.makeText(this,"OnstartCommand" + flags + " " + startId,Toast.LENGTH_SHORT).show();

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Program.stop();
        postNotification("Dying",true);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void postNotification(String message, boolean sound){
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification = new Notification.Builder(getApplicationContext(),sound?CHANNEL_ID:CHANNEL_ID_QUIET)
                    .setContentTitle("Server")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_notification)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Server")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setPriority(sound?Notification.PRIORITY_HIGH:Notification.PRIORITY_LOW)
                    .build();
        } else {
            notification = new Notification(icon, message, 0);
            notification.setLatestEventInfo(getApplicationContext(), appname, message, null);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }

        nm.notify(0, notification);

    }
    private void createNotificationsChannel(String channelId, int importance){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getResources().getString(R.string.channel_name);
            String description = getResources().getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
