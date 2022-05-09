package com.example.se_project.User;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.se_project.R;

public class UserCarCenterReservationActivity extends AppCompatActivity {
    String centerName;
    String centerAddress;
    String[] year = {"2022","2023","2024"};
    String[] month = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] date = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    private String reserveYear;
    private String reserveMonth;
    private String reserveDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_car_center_reservation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        centerName = intent.getStringExtra("centerName");
        centerAddress = intent.getStringExtra("centerAddress");
        TextView carcenterReservationTitle = findViewById(R.id.carcenterReservationTitle);
        TextView carcenterReservationAddress = findViewById(R.id.carcenterReservationAddress);
        carcenterReservationTitle.setText(centerName+" 예약");
        carcenterReservationAddress.setText(centerAddress);
        Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerYear);
        Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerMonth);
        Spinner dateSpinner = (Spinner) findViewById(R.id.spinnerDate);

        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, year);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reserveYear = year[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, month
        );
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reserveMonth = month[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, date
        );
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reserveDate = date[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        yearSpinner.setAdapter(adapterYear);
        monthSpinner.setAdapter(adapterMonth);
        dateSpinner.setAdapter(adapterDate);

    }

    private boolean validateReservation(){
        return true;
    }


}