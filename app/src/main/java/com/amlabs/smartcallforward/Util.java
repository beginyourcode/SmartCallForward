package com.amlabs.smartcallforward;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Util {

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, TestJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    public static void enableAlarm(Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);


        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTimeInMillis(System.currentTimeMillis());
        calendarStart.set(Calendar.HOUR_OF_DAY, Integer.valueOf(pref.getString("pref_start_time", context.getResources().getString(R.string.pref_default_start_time)).split(":")[0]));
        calendarStart.set(Calendar.MINUTE, Integer.valueOf(pref.getString("pref_start_time", "").split(":")[1]));
        calendarStart.set(Calendar.SECOND, 0);
        //if (calendarStart.getTimeInMillis() < System.currentTimeMillis())
        //  calendarStart.add(Calendar.DAY_OF_MONTH, 1);

        Log.d("MY_TAG", calendarStart.getTime().toString());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(System.currentTimeMillis());
        calendarEnd.set(Calendar.HOUR_OF_DAY, Integer.valueOf(pref.getString("pref_stop_time", "").split(":")[0]));
        calendarEnd.set(Calendar.MINUTE, Integer.valueOf(pref.getString("pref_stop_time", "").split(":")[1]));
        calendarEnd.set(Calendar.SECOND, 0);
        //if (calendarEnd.getTimeInMillis() < System.currentTimeMillis())
        //  calendarEnd.add(Calendar.DAY_OF_MONTH, 1);

        //Log.d("MY_TAG", calendarEnd.getTime().toString());

        AlarmManager alarmMgrStart = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmMgrStop = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intentStart = new Intent("MY_ACTION");
        intentStart.putExtra("TYPE", 1);
        final PendingIntent alarmIntentStart = PendingIntent.getBroadcast(context, 0, intentStart, PendingIntent.FLAG_ONE_SHOT);

        Intent intentStop = new Intent("MY_ACTION");
        intentStop.putExtra("TYPE", 2);
        final PendingIntent alarmIntentStop = PendingIntent.getBroadcast(context, 1, intentStop, PendingIntent.FLAG_ONE_SHOT);

        performJob(context, intentStart);
        ///TO DO - put performJob in another Intent

        alarmMgrStart.set(AlarmManager.RTC_WAKEUP, calendarStart.getTimeInMillis(), alarmIntentStart);
        //alarmMgrStop.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 80 * 1000, alarmIntentStop);

        //btnActivate.setEnabled(false);
        //btnDeActivate.setEnabled(true);

        //Enable receiver
                /*ComponentName receiver = new ComponentName(MainActivity.this, MyStartServiceReceiver.class);
                PackageManager pm = MainActivity.this.getPackageManager();

                pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);*/
                /*String callForwardString = "**21*9049206612#";
                Intent intentCallForward = new Intent(Intent.ACTION_CALL);
                Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
                intentCallForward.setData(uri2);
                startActivity(intentCallForward);*/
    }

    public static void disableAlarm(Context context) {
        AlarmManager alarmMgrStart = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmMgrStop = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intentStart = new Intent(context, MyStartServiceReceiver.class);
        intentStart.putExtra("TYPE", 1);
        final PendingIntent alarmIntentStart = PendingIntent.getBroadcast(context, 0, intentStart, PendingIntent.FLAG_ONE_SHOT);

        Intent intentStop = new Intent(context, MyStartServiceReceiver.class);
        intentStop.putExtra("TYPE", 2);
        final PendingIntent alarmIntentStop = PendingIntent.getBroadcast(context, 1, intentStop, PendingIntent.FLAG_ONE_SHOT);

        alarmMgrStart.cancel(alarmIntentStart);
        alarmMgrStop.cancel(alarmIntentStop);

    }

    private static void performJob(Context context, Intent intent)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (intent.getIntExtra("TYPE", 0) == 1) {
            String pref_prefix = pref.getString("pref_prefix", context.getString(R.string.pref_default_prefix));
            String pref_suffix = pref.getString("pref_suffix", context.getString(R.string.pref_default_suffix));
            String pref_mobile_number = pref.getString("pref_mobile_number", "");

            String callForwardString = pref_prefix + pref_mobile_number + pref_suffix;
            Log.d("MY_TAG", callForwardString);

            /*Intent intentCallForward = new Intent(Intent.ACTION_CALL);
            intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
            intentCallForward.setData(uri2);
            context.startActivity(intentCallForward);*/

            Toast.makeText(context, "Started:" + callForwardString, Toast.LENGTH_SHORT).show();
        } else if (intent.getIntExtra("TYPE", 0) == 2) {
            String pref_cancel_forward = pref.getString("pref_cancel_forward", context.getString(R.string.pref_default_cancel_forward));

            String callForwardString = pref_cancel_forward;
            Log.d("MY_TAG", callForwardString);

            /*Intent intentCallForward = new Intent(Intent.ACTION_CALL);
            intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
            intentCallForward.setData(uri2);
            context.startActivity(intentCallForward);*/

            Toast.makeText(context, "Stopped:" + callForwardString, Toast.LENGTH_SHORT).show();
        }
    }
}
