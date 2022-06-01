package com.example.se_project.init;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.CenterSignupActivity;
import com.example.se_project.R;
import com.example.se_project.UserSignupActivity;

public class SignUpActivity extends AppCompatActivity {
    Button user ,center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user=findViewById(R.id.user);
        center=findViewById(R.id.center);
        findViewById(R.id.user).setOnClickListener(onClickListener);
        findViewById(R.id.center).setOnClickListener(onClickListener);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.user:
                    StartActivity(UserSignupActivity.class);
                    break;
                case R.id.center:
                    StartActivity(CenterSignupActivity.class);
                    break;


            }
        }
    };
    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        // 동일한 창이 여러번 뜨게 만드는 것이 아니라 기존에 켜져있던 창을 앞으로 끌어와주는 기능.
        // 이 플래그를 추가하지 않을 경우 창들이 중복돼서 계속 팝업되게 된다.
        // 메인화면을 띄우는 모든 코드에서 이 플래그를 추가해줘야 하는 것 같다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
