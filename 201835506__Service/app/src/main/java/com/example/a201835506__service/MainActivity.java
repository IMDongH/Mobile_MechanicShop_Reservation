package com.example.a201835506__service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
        {
            String name =editText.getText().toString();
            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.putExtra("command","show");
            intent.putExtra("name",name);
            startService(intent);
        }
        });

    }
}