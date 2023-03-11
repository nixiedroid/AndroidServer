package com.nixiedroid.server;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import com.nixiedroid.server.server.ServerStarter;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        weakActivity = new WeakReference<>(MainActivity.this);
        if (Build.VERSION.SDK_INT > 17){
            System.out.println("WAT");
        }
        setContentView(R.layout.main_activity);
        TextView textView = findViewById(R.id.textView2);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
    public void startServer(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerStarter.start();
            }
        }).start();
    }
    public void stopServer(View view){
        ServerStarter.stop();
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