package com.nixiedroid.server.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.Toast;
import com.android.ims.internal.uce.uceservice.ImsUceManager;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.settings.LogLevel;
import com.nixiedroid.settings.ServerSettingsStub;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ServerStarter extends Service {

    boolean isRunning = false;
    public static void stop(){
        Program.stop();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ConfigStub stub = new ConfigStub(new SecretConfig());
        ServerSettingsStub settingsStub = new ServerSettingsStub(new AndroidSettings());
        settingsStub.setLevel(LogLevel.INFO);
        Program.setConfig(stub,settingsStub);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ((flags & START_FLAG_RETRY) == 0) {
            Toast.makeText(this,"Retry",Toast.LENGTH_SHORT).show();
            if (!isRunning && Program.config()!=null){
                Toast.makeText(this,"Server started",Toast.LENGTH_SHORT).show();
                Program.start();
            } else {
                Toast.makeText(this,"Server is already",Toast.LENGTH_LONG).show();
            }

            isRunning = true;
            // TODO Если это повторный запуск, выполнить какие-то действия.
        }
        else {
            Toast.makeText(this,"Strt command",Toast.LENGTH_SHORT).show();

        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                       Toast.LENGTH_SHORT).show();
        Program.stop();
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
}
