package com.nixiedroid.server.server;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.widget.Toast;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.server.MainActivity;
import com.nixiedroid.server.Notifications;
import com.nixiedroid.server.PreferencesListener;
import com.nixiedroid.settings.LogLevel;
import com.nixiedroid.settings.ServerSettingsStub;

public class ServerStarter extends Service {
    private IBinder binder;

    boolean isRunning = false;
    public static final ServerSettingsStub settings = new ServerSettingsStub(new AndroidSettings());
    Notifications notify;

    @Override
    public void onCreate() {
        super.onCreate();
        notify = new Notifications(getApplicationContext(), (NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        ConfigStub stub = new ConfigStub(new SecretConfig());
        //PreferencesListener listener =
                new PreferencesListener(getApplicationContext());
        Program.setConfig(stub,settings);

    }
    private void setup(int flags){
        notify.postNotification("Hello!",false);
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
            notify.postNotification("Hello! START",false);

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Program.stop();
        notify.postNotification("Dying",true);
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
        return binder;
    }


}
