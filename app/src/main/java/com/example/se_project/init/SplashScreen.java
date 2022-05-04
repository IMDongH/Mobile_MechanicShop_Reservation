package com.example.se_project.init;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.se_project.R;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartActivity(AuthActivity.class);
                finish();
            }
        }, 3000);
    }
    private void StartActivity(Class c) {

        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
