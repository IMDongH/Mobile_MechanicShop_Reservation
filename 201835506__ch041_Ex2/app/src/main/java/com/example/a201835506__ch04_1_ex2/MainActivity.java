package com.example.a201835506__ch04_1_ex2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get requestcode and Judge the requestcode
        if(resultCode == 200)
        {
            Log.d("TEST", String.valueOf(resultCode));
            Log.d("TEST", String.valueOf(requestCode));
            if(data!=null)
            {
                if(data != null)
                {
                    String name = data.getStringExtra("name");
                    if(name !=null)
                    {
                        Toast.makeText(this,"응답 : "+ name , Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view)
        {
            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
            startActivityForResult(intent,101);
        }
        });
    }
}