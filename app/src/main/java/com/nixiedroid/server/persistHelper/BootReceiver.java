package com.nixiedroid.server.persistHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nixiedroid.server.server.ServerService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       if ( intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
           context.startService(new Intent(context, ServerService.class));
       }
    }
}
