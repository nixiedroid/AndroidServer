package com.nixiedroid.server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.server.server.AndroidLogger;
import com.nixiedroid.server.server.AndroidSettings;
import com.nixiedroid.server.server.ServerStarter;
import com.nixiedroid.settings.LogLevel;
import com.nixiedroid.settings.ServerSettingsStub;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        weakActivity = new WeakReference<>(MainActivity.this);
        ConfigStub stub = new ConfigStub(new SecretConfig());
        ServerSettingsStub settingsStub = new ServerSettingsStub(new AndroidSettings());
        settingsStub.setLevel(LogLevel.NONE);
        Program.setConfig(stub,settingsStub);


        if (Build.VERSION.SDK_INT > 17){
            System.out.println("WAT");
        }

        setContentView(R.layout.main_activity);
        TextView textView = findViewById(R.id.textView2);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
    public void startServer(View view){
        startService(new Intent(this,ServerStarter.class));
    }
    public void stopServer(View view){
        stopService(new Intent(this,ServerStarter.class));
    }
    public void update(View view){
        TextView textView = findViewById(R.id.textView2);
        textView.setText("");
    }
    public static WeakReference<MainActivity> weakActivity;

    public static MainActivity getInstanceActivity() {
        return weakActivity.get();
    }

    public void setMessage(final String text) {
        runOnUiThread(new Runnable() {
            public void run(){
                TextView textView = findViewById(R.id.textView2);
                textView.append(text + "\n");
            }
        });
    }

}