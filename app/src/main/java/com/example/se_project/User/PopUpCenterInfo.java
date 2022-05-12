package com.example.se_project.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

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

        Button reserveButton = findViewById(R.id.reserveButton);
        Button cancelButton = findViewById(R.id.exitButton);
        reserveButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.reserveButton:
                    Intent intent = new Intent(getApplicationContext(), UserReservationActivityInit.class);
                    intent.putExtra("centerName",centerName);
                    intent.putExtra("centerAddress",address);
                    startActivity(intent);
                    break;
                case R.id.exitButton:
                    finish();
                    break;
            }
        }
    };
}