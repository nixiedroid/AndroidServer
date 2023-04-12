package com.nixiedroid.server.persistHelper;

import android.content.Context;
import android.content.Intent;
import androidx.work.*;
import com.nixiedroid.server.server.ServerService;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RestarterWorker extends Worker {

    public RestarterWorker(
            Context context,
            WorkerParameters params) {
        super(context, params);
    }
    public void  start(){
//         WorkRequest restartWorkRequest = new PeriodicWorkRequest.Builder(
//                RestarterWorker.class,
//                30,
//                TimeUnit.MINUTES,
//                25,
//                TimeUnit.MINUTES).build();
        WorkRequest restartWorkRequest  = new OneTimeWorkRequest.Builder(RestarterWorker.class).build();
        WorkManager.getInstance(getApplicationContext())
                .enqueue(restartWorkRequest);

    }

    @NotNull
    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.
        if (!ServerService.isRunning) {
            getApplicationContext(). startService(new Intent(getApplicationContext(), ServerService.class));
        }
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

}
