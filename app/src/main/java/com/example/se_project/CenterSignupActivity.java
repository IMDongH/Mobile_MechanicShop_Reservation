package com.example.se_project;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;

public class CenterSignupActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
