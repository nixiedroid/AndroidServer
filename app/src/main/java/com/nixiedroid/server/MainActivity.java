package com.nixiedroid.server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemPropertiesProto;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
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
    static private boolean isAlive = false;
    static public String START_SERVER = "START_SERVER";
    public static boolean isAlive(){
        return isAlive;
    }

    ServerStarter server;
    boolean isServerBound = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAlive = true;
        weakActivity = new WeakReference<>(MainActivity.this);
        setContentView(R.layout.main_activity);
        TextView textView = findViewById(R.id.textView2);
        textView.setMovementMethod(new ScrollingMovementMethod());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            //TODO check and ask for permission
//            getApplicationContext().checkPermission(NOTIFICATION_SERVICE)
        }
    }
    public void startServer(View view){
        Intent startIntent = new Intent(getApplicationContext(), ServerStarter.class);
        startIntent.setAction(START_SERVER);
        startService(startIntent);
       // startService(new Intent(this,ServerStarter.class));
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
        return (weakActivity==null)?null:weakActivity.get();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, Preferences.class);
            startActivity(intent);

            return (true);
        }
        return(super.onOptionsItemSelected(item));
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