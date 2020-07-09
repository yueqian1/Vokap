package com.example.wordlistapp.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.SharedPreferencesKit;
import com.example.wordlistapp.schedule.ScheduleInputActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleFragment extends Fragment {

    public static boolean scheduleSet = false;
    private String scheduleContent = "";

    private CalendarView calendarView;
    private TextView tvSchedule;
    private EditText txSchedule;
    private CardView btnAddSchedule;
    private View rootView;
    private Context context;
    private Date selectedDate;
    private Calendar calendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendar = Calendar.getInstance();
        selectedDate = calendar.getTime();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        context = getContext();

        initViews();
        initEvents();

        return rootView;
    }

    private void setText() {
        if (scheduleSet) {
            tvSchedule.setText(scheduleContent);
        }

        SharedPreferencesKit.writeString("savedSchedule", tvSchedule.getText().toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        setText();
    }

    @Override
    public void onResume() {
        super.onResume();
        setText();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setText();
    }

    private void initViews() {
        calendarView = rootView.findViewById(R.id.calendarView);
        tvSchedule = rootView.findViewById(R.id.tvSchedule);
        txSchedule = rootView.findViewById(R.id.txSchedule);
        btnAddSchedule = rootView.findViewById(R.id.btnAddSchedule);

        tvSchedule.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));
        txSchedule.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));

        String savedSchedule = SharedPreferencesKit.loadString("savedSchedule", "No schedule ~");
        tvSchedule.setText(savedSchedule);
    }

    private void initEvents() {

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txSchedule.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "Write something!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(context, ScheduleInputActivity.class);

                intent.putExtra("year", selectedDate.getYear());
                intent.putExtra("month", selectedDate.getMonth());
                intent.putExtra("day", selectedDate.getDay());

                scheduleContent = txSchedule.getText().toString();
                scheduleSet = false;
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = new Date(year, month, dayOfMonth);
            }
        });
    }

}
