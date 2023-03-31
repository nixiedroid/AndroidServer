package com.nixiedroid.server.server;


import com.nixiedroid.server.MainActivity;

public class AndroidLogger implements com.nixiedroid.logger.Logger {
    private void setMessage(String s){
        if (activity!= null) activity.setMessage(s);
    }
    MainActivity activity = MainActivity.getInstanceActivity();
    @Override
    public void err(String s) {
        setMessage(s);
    }

    @Override
    public void debug(String s) {
        setMessage(s);
    }

    @Override
    public void info(String s) {
        setMessage(s);
    }

    @Override
    public void verbose(String s) {
        setMessage(s);
    }
}
