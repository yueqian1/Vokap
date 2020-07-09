package com.example.wordlistapp.schedule;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.wordlistapp.R;

import java.util.Calendar;
import java.util.Date;

public class ScheduleInputActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private Date due;
    private Button RingOne_btn;
    private Button RingCycle_btn;
    private Button SendNofication_btn;
    private Button CancleRingCycle_btn;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wordtest);
        //初始化视图
        initview();
        initEvents();
    }


    //初始化视图
    private void initview() {
        RingOne_btn = findViewById(R.id.btnWordTestChoice1);
        RingCycle_btn = findViewById(R.id.btnWordTestChoice2);
        SendNofication_btn = findViewById(R.id.btnWordTestChoice3);
        CancleRingCycle_btn = findViewById(R.id.btnWordTestChoice4);

        RingOne_btn.setBackgroundResource(R.color.wordTestChoiceDefault);
        RingCycle_btn.setBackgroundResource(R.color.wordTestChoiceDefault);
        CancleRingCycle_btn.setBackgroundResource(R.color.wordTestChoiceDefault);
        SendNofication_btn.setBackgroundResource(R.color.wordTestChoiceDefault);

        RingOne_btn.setText("闹铃提醒（单次）");
        RingCycle_btn.setText("闹铃提醒（周期）");
        SendNofication_btn.setText("定时通知");
        CancleRingCycle_btn.setText("取消提醒");


        Button button3 = findViewById(R.id.button3);                  //自定义----------------------------------

        //获取闹钟管理者
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private PendingIntent getPendingIntent() {
        //发送广播
        Intent intent = new Intent(ScheduleInputActivity.this, RingActivity.class);

        if (Build.VERSION.SDK_INT >= 26) {
            ComponentName componentName = new ComponentName(getApplicationContext(), "com.example.wordlistapp.schedule.RingReceiver");
            intent.setComponent(componentName);
        }

        //将来时态的跳转
        pendingIntent = PendingIntent.getBroadcast(ScheduleInputActivity.this, 0, intent, 0);
        return pendingIntent;
    }

    private void initEvents() {
        //单次闹铃
        RingOne_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前的系统时间
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                //弹出时间对话框
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleInputActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //选择闹钟启用的时间
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        //c.add(Calendar.SECOND, 10);
                        c.set(Calendar.HOUR_OF_DAY, i);
                        c.set(Calendar.MINUTE, i1);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        //使用alarmManager设置闹钟

                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), getPendingIntent());

                        finish();
                    }
                }, hour, minute, true);

                ScheduleFragment.scheduleSet = true;
                timePickerDialog.show();
            }

        });

        //重复闹钟
        RingCycle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前的系统时间
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                //弹出时间对话框
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleInputActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //选择闹钟启用的时间
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        //c.add(Calendar.SECOND, 10);
                        c.set(Calendar.HOUR_OF_DAY, i);
                        c.set(Calendar.MINUTE, i1);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        //使用alarmManager设置循环闹钟
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, getPendingIntent());

                        finish();
                    }
                }, hour, minute, true);

                ScheduleFragment.scheduleSet = true;
                timePickerDialog.show();
            }
        });


        //取消重复闹钟
        CancleRingCycle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pendingIntent != null) {
                    alarmManager.cancel(pendingIntent);
                }

                ScheduleFragment.scheduleSet = false;
                finish();
            }
        });


        //发送通知
        SendNofication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实例化通知管理者
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                //实例化通知
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ScheduleInputActivity.this);

                builder.setContentTitle("哈哈");
                builder.setContentText("今天放假");
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);

                builder.setAutoCancel(true);
                builder.setSmallIcon(android.R.drawable.ic_menu_delete);

                builder.setContentIntent(PendingIntent.getActivity(
                        ScheduleInputActivity.this,
                        0x220,
                        new Intent(ScheduleInputActivity.this, RingActivity.class),
                        0
                ));

                Notification notification = builder.build();

                //发送通知
                ScheduleFragment.scheduleSet = true;
                notificationManager.notify(0x111, notification);

                finish();
            }
        });
    }

}
