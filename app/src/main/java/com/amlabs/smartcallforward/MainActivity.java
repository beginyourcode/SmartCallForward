package com.amlabs.smartcallforward;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnActivate, btnDeActivate;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    //AlarmManager alarmMgrStart, alarmMgrStop;
    TextView tv_start, tv_stop;
    WeekdaysPicker widget;
    CheckBox chk_repeat;
    TextInputEditText et_mobile_number;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnActivate = findViewById(R.id.btnActivate);
        btnDeActivate = findViewById(R.id.btnDeActivate);
        tv_start = findViewById(R.id.tv_start);
        tv_stop = findViewById(R.id.tv_stop);
        widget = findViewById(R.id.weekdays);
        chk_repeat = findViewById(R.id.chk_repeat);
        et_mobile_number = findViewById(R.id.et_mobile_number);
        //requestPermission();
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},//, Manifest.permission.READ_PHONE_STATE,, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.GET_TASKS
                1);

        pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        et_mobile_number.setText(pref.getString("pref_mobile_number", ""));
        tv_start.setText(pref.getString("pref_start_time", "12:00"));
        tv_stop.setText(pref.getString("pref_end_time", "13:00"));
        //btnActivate.setEnabled(!pref.getBoolean("pref_started", false));
        //btnDeActivate.setEnabled(pref.getBoolean("pref_started", false));

        /*alarmMgrStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmMgrStop = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intentStart = new Intent(MainActivity.this, MyStartServiceReceiver.class);
        intentStart.putExtra("TYPE", 1);
        final PendingIntent alarmIntentStart = PendingIntent.getBroadcast(MainActivity.this, 0, intentStart, PendingIntent.FLAG_ONE_SHOT);

        Intent intentStop = new Intent(MainActivity.this, MyStartServiceReceiver.class);
        intentStop.putExtra("TYPE", 2);
        final PendingIntent alarmIntentStop = PendingIntent.getBroadcast(MainActivity.this, 1, intentStop, PendingIntent.FLAG_ONE_SHOT);
*/


        /*Intent intentStart1 = new Intent(MainActivity.this, MyStartServiceReceiver.class);
        boolean startIsWorking = (PendingIntent.getBroadcast(MainActivity.this, 0, intentStart1, PendingIntent.FLAG_NO_CREATE) != null);
        Log.d("MY_TAG", "start is " + (startIsWorking ? "" : "not") + " working...");*/

        /*boolean stopIsWorking = (PendingIntent.getBroadcast(MainActivity.this, 1, intentStop, PendingIntent.FLAG_NO_CREATE) != null);
        Log.d("MY_TAG", "stop is " + (stopIsWorking ? "" : "not") + " working...");*/
        /*alarmMgrStart.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);*/


        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("pref_mobile_number", et_mobile_number.getText().toString());
                editor.putString("pref_start_time", tv_start.getText().toString());
                editor.putString("pref_end_time", tv_stop.getText().toString());
                editor.putBoolean("pref_started", true);
                editor.commit();
                //Util.enableAlarm(MainActivity.this);

                //Broadcast ur Intent here
                Intent intent  = new Intent("MY_ACTION");
                MainActivity.this.sendBroadcast(intent);


            }
        });
        btnDeActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alarmMgrStart.cancel(alarmIntentStart);
                //alarmIntentStart.cancel();
                //alarmMgrStop.cancel(alarmIntentStop);
                //alarmIntentStop.cancel();

                //btnActivate.setEnabled(true);
                //btnDeActivate.setEnabled(false);
                //Util.disableAlarm(MainActivity.this);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("pref_started", false);
                editor.commit();

                /*ComponentName receiver = new ComponentName(MainActivity.this, MyStartServiceReceiver.class);
                PackageManager pm = MainActivity.this.getPackageManager();

                pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);*/

                /*String callForwardString = "##21#";
                Intent intentCallForward = new Intent(Intent.ACTION_CALL);
                Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
                intentCallForward.setData(uri2);
                startActivity(intentCallForward);*/
            }
        });
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = Integer.valueOf(tv_start.getText().toString().split(":")[0]);
                int minute = Integer.valueOf(tv_start.getText().toString().split(":")[1]);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tv_start.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        tv_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = Integer.valueOf(tv_stop.getText().toString().split(":")[0]);
                int minute = Integer.valueOf(tv_stop.getText().toString().split(":")[1]);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tv_stop.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
                // Do Something
            }
        });
        chk_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    widget.setVisibility(View.VISIBLE);
                else
                    widget.setVisibility(View.GONE);

            }
        });
    }

    protected void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
