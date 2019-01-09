package com.amlabs.smartcallforward;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class TestJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(getApplicationContext(),"I am executed",Toast.LENGTH_SHORT).show();
        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
