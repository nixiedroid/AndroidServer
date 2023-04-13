package com.nixiedroid.server.persistHelper.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerStarter {
    Context context;
    JobScheduler jobScheduler;
    private static final int jobId = 4242;


    public JobSchedulerStarter(Context context) {
        this.context = context;
        jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }
    public void stop(){
        if (isJobIdRunning()){
            jobScheduler.cancel(jobId);
        }
    }

    public void start(){
         if (!isJobIdRunning()){
             ComponentName jobService = new ComponentName(context, Restarter.class);
             JobInfo.Builder restarterBuilder = new JobInfo.Builder(jobId, jobService)
                     .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                     .setRequiresDeviceIdle(false)
                     .setRequiresCharging(false)
                     .setPeriodic(900_000);//10 mins
             int result = jobScheduler.schedule(restarterBuilder.build());
         }
    }

    public boolean isJobIdRunning() {
        for ( JobInfo jobInfo : jobScheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == jobId ) {
                return true;
            }
        }
        return false;
    }

}
