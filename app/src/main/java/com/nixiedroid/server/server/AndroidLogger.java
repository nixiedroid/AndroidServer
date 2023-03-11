package com.nixiedroid.server.server;


import com.nixiedroid.server.MainActivity;

public class AndroidLogger implements com.nixiedroid.logger.Logger {
    MainActivity activity = MainActivity.getInstanceActivity();
    @Override
    public void err(String s) {
        activity.setMessage(s);
    }

    @Override
    public void debug(String s) {
        activity.setMessage(s);
    }

    @Override
    public void info(String s) {
        activity.setMessage(s);
    }

    @Override
    public void verbose(String s) {
        activity.setMessage(s);
    }
}
