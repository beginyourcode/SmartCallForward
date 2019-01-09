package com.amlabs.smartcallforward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class MyStartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Util.scheduleJob(context);
        // Set the alarm here.
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Util.enableAlarm(context);
        }
        if (intent.getAction().equals("MY_ACTION")) {
            Util.enableAlarm(context);
        }
        /*SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (intent.getIntExtra("TYPE", 0) == 1) {
            String pref_prefix = pref.getString("pref_prefix", context.getString(R.string.pref_default_prefix));
            String pref_suffix = pref.getString("pref_suffix", context.getString(R.string.pref_default_suffix));
            String pref_mobile_number = pref.getString("pref_mobile_number", "");

            String callForwardString = pref_prefix + pref_mobile_number + pref_suffix;
            Log.d("MY_TAG", callForwardString);

            *//*Intent intentCallForward = new Intent(Intent.ACTION_CALL);
            intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
            intentCallForward.setData(uri2);
            context.startActivity(intentCallForward);*//*

            Toast.makeText(context, "Started:" + callForwardString, Toast.LENGTH_SHORT).show();
        } else if (intent.getIntExtra("TYPE", 0) == 2) {
            String pref_cancel_forward = pref.getString("pref_cancel_forward", context.getString(R.string.pref_default_cancel_forward));

            String callForwardString = pref_cancel_forward;
            Log.d("MY_TAG", callForwardString);

            *//*Intent intentCallForward = new Intent(Intent.ACTION_CALL);
            intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
            intentCallForward.setData(uri2);
            context.startActivity(intentCallForward);*//*

            Toast.makeText(context, "Stopped:" + callForwardString, Toast.LENGTH_SHORT).show();
        }*/


    }
}
