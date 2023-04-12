package com.nixiedroid.server;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.nixiedroid.server.server.AndroidLogger;
import com.nixiedroid.server.server.ServerService;

import java.util.LinkedList;


public class MainActivity extends Activity {
    MessagesUpdater updater;
    TextView textView;
    Button startStopButton;
   // private Worker worker;

    @Override
    protected void onDestroy() {
        updater.destroyUpdater();
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        updater.destroyUpdater();
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        updater = new MessagesUpdater(textView, AndroidLogger.getMessages());
        updater.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        startStopButton = findViewById(R.id.startStopButton);
        if (ServerService.isRunning) startStopButton.setText(R.string.stop_name);

        textView = findViewById(R.id.serverLog);
        textView.setMovementMethod(new ScrollingMovementMethod());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission();
        }
       // worker  = new RestarterWorker(getApplicationContext(),);
    }

    public void startStopServer(View view) {
        if (ServerService.isRunning) {
            stopService(new Intent(this, ServerService.class));
            if (!ServerService.isRunning) startStopButton.setText(R.string.start_name);
        } else {
            startService(new Intent(this, ServerService.class));
            if (!ServerService.isRunning) startStopButton.setText(R.string.stop_name);
        }

    }
    private void checkNotificationPermission(){
        //TODO check and ask for permission
        //getApplicationContext().checkPermission(NOTIFICATION_SERVICE)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        return (super.onOptionsItemSelected(item));
    }


    public class MessagesUpdater extends Thread {

        private final TextView textView;
        private final LinkedList<String> messages;
        private boolean isRunning = true;

        public MessagesUpdater(TextView textView, LinkedList<String> messages) {
            this.textView = textView;
            this.messages = messages;
        }

        public void destroyUpdater() {
            isRunning = false;
        }
        public void update() {
            textView.setText("");
            for (String str : messages) {
                textView.append(str + "\n");
            }
        }

        public void run() {
            try {
                while (isRunning) {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    });
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}