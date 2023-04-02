package com.nixiedroid.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nixiedroid.server.server.ServerStarter;

public class BootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServerStarter.class));
    }
}
