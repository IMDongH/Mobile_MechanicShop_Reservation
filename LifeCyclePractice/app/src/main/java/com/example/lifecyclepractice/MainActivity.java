package com.example.lifecyclepractice;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    String TAG = "LifeCycle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"In the onCreate event");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"In the onStop event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"In the onDestroy event");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"In the onResume event");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"In the onPause event");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"In the onStart event");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"In the onRestart event");
    }
}