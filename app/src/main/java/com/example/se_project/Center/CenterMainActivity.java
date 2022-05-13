package com.example.se_project.Center;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.LoginActivity;
import com.example.se_project.R;
import com.example.se_project.User.Search.UserSearchActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CenterMainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private Toast terminate_guide_msg;

    Calendar myCalendar = Calendar.getInstance();
    String selected_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("예약관리");

        // selected_Date 정보를 이용하여 예약 정보를 DB 에서 가져오고 리스트뷰 어뎁터에 넘겨준다
        // db.collection(아마 reservation).get. 해서 문서 다가오고 -> 센터이름 && 날짜 일치하면 -> 어뎁터에 정보 넘기고 -> 화면에 리스트뷰 뿌리면 될듯
        // 센터 이름은 db.collection(enterprises).document(users.getUid()).get().addOnCompleteListener 해서 document.get("centerName")로 가져오면 될듯
        // test
    }

    // 선택한 날짜를 myCalendar 에 설정해준다
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2022/05/12
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        selected_Date = sdf.format(myCalendar.getTime());

        TextView et_date = (TextView) findViewById(R.id.textview);
        et_date.setText(sdf.format(myCalendar.getTime())); // 선택한 날짜 정보를 가져올 때 쓰면 된다
    }


    // 로그아웃, 날짜 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.LogoutMenu:
                FirebaseAuth.getInstance().signOut();
                StartActivity(LoginActivity.class);
                break;

            case R.id.SettingMenu:
                Toast.makeText(getApplicationContext(), "설정 메뉴", Toast.LENGTH_SHORT).show();
                break;

            case R.id.calendar:
                // 오늘 날짜로 Dialog 를 활성화한다
                new DatePickerDialog(CenterMainActivity.this, myDatePicker,
                                                    myCalendar.get(Calendar.YEAR),
                                                    myCalendar.get(Calendar.MONTH),
                                                    myCalendar.get(Calendar.DAY_OF_MONTH))
                                                    .show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 상단 엑션바에 메뉴바 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu_center, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        // 동일한 창이 여러번 뜨게 만드는 것이 아니라 기존에 켜져있던 창을 앞으로 끌어와주는 기능.
        // 이 플래그를 추가하지 않을 경우 창들이 중복돼서 계속 팝업되게 된다.
        // 메인화면을 띄우는 모든 코드에서 이 플래그를 추가해줘야 하는 것 같다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
            backKeyPressedTime = System.currentTimeMillis();
            terminate_guide_msg = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            terminate_guide_msg.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
            terminate_guide_msg.cancel();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }}