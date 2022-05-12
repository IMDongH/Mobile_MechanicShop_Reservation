package com.example.se_project.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserReservationActivityInit extends AppCompatActivity
{
    String readDay = null;
    CalendarView calendarView;
    Button next_Btn;
    String centerName, centerAddress;
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_init);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //Date 객체 사용
        java.util.Date today = new Date();
        Date = simpleDateFormat.format(today);

        ActionBar ac = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendarView = findViewById(R.id.calendarView);
        next_Btn = findViewById(R.id.next_Btn);


        Intent intent = getIntent();

        centerName = intent.getStringExtra("centerName");
        centerAddress = intent.getStringExtra("centerAddress");
        TextView carcenterReservationTitle = findViewById(R.id.carcenterReservationTitle);
        carcenterReservationTitle.setText(centerName);
        ac.setTitle("예약하기");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                next_Btn.setVisibility(View.VISIBLE);

                Date = checkDay(year, month, dayOfMonth);
            }
        });
        next_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), UserReservationActivity.class);
                intent.putExtra("centerName", centerName);
                intent.putExtra("centerAddress", centerAddress);
                intent.putExtra("date",Date);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public String checkDay(int cYear, int cMonth, int cDay)
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        String date = cYear + "-" + (cMonth + 1) + "" + "-" + cDay;
        try
        {
            next_Btn.setVisibility(View.VISIBLE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

}