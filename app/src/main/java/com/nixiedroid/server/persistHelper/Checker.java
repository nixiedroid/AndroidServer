package com.nixiedroid.server.persistHelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.nixiedroid.server.server.ServerService;

public class Checker {
    private final Context context;

    public Checker(Context context) {
        this.context = context;
    }

    public void checkAndRestartIfNeeded(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!ServerService.isRunning) {
            if (preferences.getBoolean("isRunning", false)) {
                context.startService(new Intent(context, ServerService.class));
            }
        }
    }
}
