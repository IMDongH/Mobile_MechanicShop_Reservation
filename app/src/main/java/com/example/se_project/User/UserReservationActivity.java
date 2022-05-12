package com.example.se_project.User;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.se_project.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserReservationActivity extends AppCompatActivity {

    String centerName, centerAddress, date;
    Button complete_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);


        ActionBar ac = getSupportActionBar();
        ac.setTitle("예약하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        centerName = intent.getStringExtra("centerName");
        centerAddress = intent.getStringExtra("centerAddress");
        date = intent.getStringExtra("date");
        complete_Btn = findViewById(R.id.complete_Btn);
        TextView carcenterReservationTitle = findViewById(R.id.carcenterReservationTitle);
        carcenterReservationTitle.setText(centerName);

        complete_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent intent = new Intent(getApplicationContext(), UserReservationActivity.class);
//
//                startActivity(intent);
                finish();
                finish();
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


}