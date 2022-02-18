package com.example.cameraexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class QR_Defensive extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defensive_qr);
        Intent intent = new Intent(getApplicationContext(), ScanQR.class);
        ImageButton defensive_button = (ImageButton) findViewById(R.id.defensive_button);
        defensive_button.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                startActivity(intent);
            }
        });
    }
}