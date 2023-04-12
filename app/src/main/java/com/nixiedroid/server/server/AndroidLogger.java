package com.nixiedroid.server.server;

import java.util.LinkedList;

public class AndroidLogger implements com.nixiedroid.logger.Logger {

    private static final LinkedList<String> messages = new LinkedList<>();

    private void setMessage(String s){
        if (messages.size()>=20) messages.poll();
        messages.add(s);
    }
    public static LinkedList<String> getMessages(){
        return messages;
    }

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
