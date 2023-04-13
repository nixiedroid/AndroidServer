package com.nixiedroid.server.persistHelper.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import com.nixiedroid.server.persistHelper.Checker;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Restarter  extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Checker checker = new Checker(getApplicationContext());
        checker.checkAndRestartIfNeeded();
        jobFinished(params,true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
