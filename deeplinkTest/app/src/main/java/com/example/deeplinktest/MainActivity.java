package com.example.deeplinktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    String TAG = "Dong";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String action =intent.getAction();
        String data = intent.getDataString();
        Log.w(TAG,"action : " + action);
        Log.w(TAG,"data : " + data);
        TextView text=findViewById(R.id.text);
        final String DEFAULT_PATH = "https://dongdeeptest.page.link";

        if (action!=null && data != null) {
            if (data.startsWith(DEFAULT_PATH)) {
                String param = data.replace(DEFAULT_PATH, "");
                text.setText(param);
                Log.w(TAG,param);
            }
            else
            {
                Log.w(TAG,"nonononono");
            }
        }
    }
}