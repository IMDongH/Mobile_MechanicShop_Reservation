package com.example.se_project.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.se_project.R;

public class PopUpCenterInfo extends Activity {
    String centerName ;
    String address;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_pop_up_center_info);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        centerName = data.getString("centerName");
        address = data.getString("centerAddress");
        phone = data.getString("phone");


        TextView centerNameTextView = findViewById(R.id.centerNameTextView);
        TextView centerAddressTextView = findViewById(R.id.centerAddressTextView);
        TextView phoneNumberTextView = findViewById(R.id.phoneNumberTextView);

        centerNameTextView.setText(centerName);
        centerAddressTextView.setText(address);
        phoneNumberTextView.setText(phone);

        phone = "tel:"+phone.substring(0,3)+"-"+phone.substring(3,7)+"-"+phone.substring(7);
        Button reserveButton = findViewById(R.id.reserveButton);
        Button cancelButton = findViewById(R.id.exitButton);
        Button phoneButton = findViewById(R.id.phoneCallButton);

        reserveButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
        phoneButton.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.reserveButton:
                    Intent intent = new Intent(getApplicationContext(), UserReservationActivity.class);
                    intent.putExtra("centerName",centerName);
                    intent.putExtra("centerAddress",address);
                    startActivity(intent);
                    break;
                case R.id.exitButton:
                    finish();
                    break;
                case R.id.phoneCallButton:
                    Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(phone));
                    startActivity(callIntent);
            }
        }
    };
}