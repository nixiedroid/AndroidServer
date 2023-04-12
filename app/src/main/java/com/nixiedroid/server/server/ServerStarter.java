package com.nixiedroid.server.server;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.server.Notifications;
import com.nixiedroid.settings.ServerSettingsStub;

public class ServerStarter extends Service implements SharedPreferences.OnSharedPreferenceChangeListener  {

    public static boolean isRunning = false;
    private Notifications notify;
    private ServerSettingsStub settings;
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(this);
        notify = new Notifications(getApplicationContext(), (NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        loadSettings();
        ConfigStub stub = new ConfigStub(new SecretConfig());
        Program.setConfig(stub, settings);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notify.postNotification("Hello!", false);
        if ((flags & START_FLAG_RETRY) == 0) {
            if (!isRunning) {
                Toast.makeText(this, "Server started", Toast.LENGTH_SHORT).show();
                Program.start();
                isRunning = true;
            } else {
                Toast.makeText(this, "Server is already started", Toast.LENGTH_SHORT).show();
            }
        } else {
            notify.postNotification("Hello! START", false);

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Program.stop();
        notify.postNotification("Server Dead!", true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("portSettings")){
            if (isRunning) {
                Program.stop();
                settings.setPort(Integer.parseInt(sharedPreferences.getString(key, "1688")));
                Program.start();
            } else settings.setPort(Integer.parseInt(sharedPreferences.getString(key, "1688")));
        }
    }
    private void loadSettings(){
        settings = new ServerSettingsStub(new AndroidSettings());
        settings.setPort(Integer.parseInt(preferences.getString("portSettings", "1688")));
    }

}
