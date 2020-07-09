package com.example.wordlistapp.schedule;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.SharedPreferencesKit;

public class RingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedfreferences;
        TextView textView;                                      //-----------------------------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);

        textView = findViewById(R.id.tvRingContent);

        String schedule = SharedPreferencesKit.loadString("savedSchedule", "Oops!");
        textView.setText(schedule);
        //初始化控件
        initview();
    }

    private void initview() {
        CardView stop_btn = findViewById(R.id.stop_btn);

        mediaPlayer = MediaPlayer.create(RingActivity.this, R.raw.abc);
        mediaPlayer.start();

        //停止音乐
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
            }
        });
    }
}
