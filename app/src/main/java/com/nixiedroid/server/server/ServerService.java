package com.nixiedroid.server.server;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.server.Notifications;
import com.nixiedroid.server.persistHelper.jobscheduler.JobSchedulerStarter;
import com.nixiedroid.settings.ServerSettingsStub;

public class ServerService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener  {

    public static boolean isRunning = false;
    private static boolean isStoppedByUser = false;
    private Notifications notify;
    private ServerSettingsStub settings;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private JobSchedulerStarter jobStarter;


    @Override
    public void onCreate() {
        super.onCreate();
        preferences =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       initRestarter();
        preferences.registerOnSharedPreferenceChangeListener(this);
        editor = preferences.edit();
        notify = new Notifications(getApplicationContext(), (NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        loadSettings();
        ConfigStub stub = new ConfigStub(new SecretConfig());
        Program.setConfig(stub, settings);

    }

    private void initRestarter(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobStarter = new JobSchedulerStarter(getApplicationContext());
        } else {
            //TODO use AlarmManager
        }
    }

    private void persistenceHacks(boolean enabled){
      editor.putBoolean("isRunning",enabled);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else editor.commit();
      if(enabled){
          jobStarter.start();
      } else  jobStarter.stop();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notify.postNotification("Hello!", false);
        if ((flags & START_FLAG_RETRY) == 0) {
            if (!isRunning) {
                Program.start();
                isRunning = true;
                persistenceHacks(true);
            }
        } else {
            notify.postNotification("Hello! START", false);
        }
        return START_STICKY;
    }

    public static void setUserStop(){
        isStoppedByUser = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        if (isStoppedByUser){
            persistenceHacks(false);
        }
        Program.stop();
        notify.postNotification("Server Stopped!", true);
        isStoppedByUser = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("portSettings")){
            int port  = Integer.parseInt(sharedPreferences.getString(key, "1688"));
            if (isRunning) {
                Program.stop();
                settings.setPort(port);
            } else settings.setPort(port);
        }
    }
    private void loadSettings(){
        settings = new ServerSettingsStub(new AndroidSettings());
        settings.setPort(Integer.parseInt(preferences.getString("portSettings", "1688")));
    }

}
