package com.nixiedroid.server;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.nixiedroid.Program;
import com.nixiedroid.SecretConfig;
import com.nixiedroid.confg.ConfigStub;
import com.nixiedroid.server.server.AndroidSettings;
import com.nixiedroid.settings.ServerSettingsStub;
import org.jetbrains.annotations.NotNull;

public class BackgroundServer extends Worker {
    public BackgroundServer(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        Program.stop();
        ConfigStub stub = new ConfigStub(new SecretConfig());
        ServerSettingsStub settingsStub = new ServerSettingsStub(new AndroidSettings());
        settingsStub.
        Program.setConfig(stub, settingsStub);
        Program.start();
    }
}
