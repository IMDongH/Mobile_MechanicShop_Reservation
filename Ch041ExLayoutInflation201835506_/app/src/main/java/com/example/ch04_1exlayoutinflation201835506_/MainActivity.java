package com.example.ch04_1exlayoutinflation201835506_;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ch04_1exlayoutinflation201835506_.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        container = findViewById(R.id.container);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addlayout();
        }});
        }
    public void addlayout(){
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        inflater.inflate(R.layout.sub1,container,true);

        Toast.makeText(this,"부분추가",Toast.LENGTH_LONG).show();
    }
}