package com.example.se_project.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserReservationActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    final String[] selectOption = {"가평군", "고양시", "구리시", "김포시", "남양주시",
            "부천시", "성남시", "수원시", "시흥시", "안산시",
            "안양시", "양주시", "양평군", "여주시", "연천군시",
            "오산시", "용인시", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "하남시", "화성시"};
    String city;
    String today;
    String readDay = null;
    CalendarView calendarView;
    Button complete_Btn;
    String centerName, centerAddress;
    String Date;

    ArrayList<String> time = new ArrayList<>();

    String[] content = {"예약 목적 선택","자동차 수리","부품 교체", "자동차 검사"};
    UserReservationInfo info = new UserReservationInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        time.add("예약 시간 선택");
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        //Date 객체 사용
        java.util.Date day = new Date();
        Date = simpleDateFormat.format(day);
        today = Date;
        info.setDate(Date);
        info.setUserId(user.getUid());
        ActionBar ac = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendarView = findViewById(R.id.calendarView);
        complete_Btn = findViewById(R.id.complete_Btn);

        Spinner reservationSpinner = (Spinner) findViewById(R.id.reservation);
        Spinner contentSpinner = (Spinner) findViewById(R.id.content);
        Intent intent = getIntent();

        centerName = intent.getStringExtra("centerName");
        info.setCenterName(centerName);
        centerAddress = intent.getStringExtra("centerAddress");
        info.setAddress(centerAddress);



        db.collection("enterprises").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        if (document.exists()){
                            String name = (String) document.getData().get("centerName");
                            String address = (String) document.getData().get("roadName_Address");
                            System.out.println(name + info.getCenterName());
                            System.out.println(address + info.getAddress());
                            if(name.equals(info.getCenterName())&&address.equals(info.getAddress()))
                            {

                                String start = (String) document.getData().get("startTime");
                                String end = (String) document.getData().get("endTime");
                                int startH;
                                int startM;
                                int endH;
                                int endM;
                                int openH;
                                int openM;

                                String[] startT = start.split(" ");

                                if(startT[0].equals("AM")){
                                    String intStr = startT[1].replaceAll("[^0-9]", "");
                                    startH=Integer.parseInt(intStr);
                                    String intStr2 = startT[2].replaceAll("[^0-9]", "");
                                    startM=Integer.parseInt(intStr2);
                                    if(startM>=30)
                                    {
                                        startM=30;
                                    }
                                    else
                                    {
                                        startM=0;
                                    }
                                }
                                else
                                {

                                    String intStr = startT[1].replaceAll("[^0-9]", "");
                                    startH=Integer.parseInt(intStr)+12;
                                    String intStr2 = startT[2].replaceAll("[^0-9]", "");
                                    startM=Integer.parseInt(intStr2);
                                    if(startM>=30)
                                    {
                                        startM=30;
                                    }
                                    else
                                    {
                                        startM=0;
                                    }
                                }

                                String[] endT = end.split(" ");
                                if(endT[0].equals("AM")){
                                    String intStr = endT[1].replaceAll("[^0-9]", "");
                                    endH=Integer.parseInt(intStr);
                                    String intStr2 = endT[2].replaceAll("[^0-9]", "");
                                    endM=Integer.parseInt(intStr2);
                                    if(endM>=30)
                                    {
                                        endM=30;
                                    }
                                    else
                                    {
                                        endM=0;
                                    }
                                }
                                else
                                {

                                    String intStr = endT[1].replaceAll("[^0-9]", "");
                                    endH=Integer.parseInt(intStr)+12;
                                    String intStr2 = endT[2].replaceAll("[^0-9]", "");
                                    endM=Integer.parseInt(intStr2);
                                    if(endM>=30)
                                    {
                                        endM=30;
                                    }
                                    else
                                    {
                                        endM=0;
                                    }
                                }

                                openH=(endH-startH)*2;
                                openM=endM-startM;
                                if(openM==30)
                                {
                                    openH++;
                                }
                                ArrayList<Integer>[] test = new ArrayList[openH];
                                for(int i=0; i<openH; i++)
                                {
                                    test[i]=new ArrayList<Integer>();
                                }

                                for(int i=0; i<openH; i++)
                                {
                                    if(startM==30)
                                    {
                                        startH++;
                                        startM=0;
                                    }
                                    else
                                    {
                                        startM=30;
                                    }
                                    test[i].add(startH);
                                    test[i].add(startM);
                                }
                                for(int i=0; i<openH; i++)
                                {

                                    time.add(test[i].get(0)+ " : "+test[i].get(1));
                                }
                            }
                        }else{
                            Log.d("Wrong","No document");
                        }

                    }

                    }
                    else{
                    Log.d("Wrong", "failed with", task.getException());
                }
            }
        });

        TextView carcenterReservationTitle = findViewById(R.id.carcenterReservationTitle);
        carcenterReservationTitle.setText(centerName);
        ac.setTitle("예약하기");

        findViewById(R.id.complete_Btn).setOnClickListener(onClickListener);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
//                complete_Btn.setVisibility(View.VISIBLE);
                Date = checkDay(year, month, dayOfMonth);
                info.setDate(Date);
            }
        });
        ArrayAdapter<String> adapterReservation = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, time
        );
        reservationSpinner.setAdapter(adapterReservation);
        adapterReservation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reservationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("TESTTEST", time.get(i));
                info.setTime(time.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterContent = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, content
        );
        contentSpinner.setAdapter(adapterContent);
        adapterContent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("TESTTEST", content[i]);
                info.setContent(content[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 회원가입 버튼
                case R.id.complete_Btn:
                        info.setName(((EditText) findViewById(R.id.userNameReservation)).getText().toString());
                        info.setPhone(((EditText) findViewById(R.id.phoneReservation)).getText().toString());
                        info.setCarNumber(((EditText) findViewById(R.id.carNumberReservation)).getText().toString());
                        info.setCarType(((EditText) findViewById(R.id.carModelReservation)).getText().toString());
                    if(CheckReservationInfoCondition(info)==true)
                    {
                        info.setUserId(user.getUid());
                        dbInsertion(info);
                        finish();
                    }
                    break;
            }
        }
    };

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
    public String checkDay(int cYear, int cMonth, int cDay)
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        String date = null;
        if(cMonth+1<10)
        {
            date = cYear + "-" + "0"+(cMonth + 1) + "" + "-" + cDay;
        }
        else if(cDay<10)
        {
            date = cYear + "-" + (cMonth + 1) + "" + "-" + "0"+cDay;
        }
        else if(cMonth+1<10&&cDay<10)
        {
            date = cYear + "-" + "0"+(cMonth + 1) + "" + "-" + "0"+cDay;
        }
        else
        {
             date = cYear + "-" + (cMonth + 1) + "" + "-" + cDay;
        }

        try
        {
            complete_Btn.setVisibility(View.VISIBLE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }
    private boolean CheckReservationInfoCondition(UserReservationInfo info) {
        System.out.println("TEST"+info.getTime());
        if (info.getTime()==null||info.getTime().contains("예약 시간 선택")) {
            StartToast("예약 시간을 선택해주세요");
            return false;}
        else if (info.getName().length() <= 0) {
            StartToast("예약자 이름의 길이를 확인해주세요 : 1자 이상");
            return false;
        } else if (info.getPhone().length() < 11) {
            StartToast("휴대폰의 길이를 확인해주세요 : 11자 이상");
            return false;
        } else if (info.getCarNumber().length() < 4) {
            StartToast("차량 번호의 길이를 확인해주세요 : 4자 이상");
            return false;
        } else if (info.getCarType().length() == 0 ) {
            StartToast("차량 기종을 입력해주세요");
            return false;
        }
        else if (info.getContent()==null||info.getContent().contains("예약 목적 선택")) {
            StartToast("예약 목적을 선택해주세요.");
            return false;
        }
        else
        {
            return true;
        }
    }

    private void dbInsertion(UserReservationInfo info) {

    db.collection("reservation").document(info.getCenterName()).collection(info.getDate()).document(info.getTime()).set(info)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StartToast("예약에 성공 했습니다.");

                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    StartToast("예약에 실패했습니다");
                }
            });

        db.collection("users").document(user.getUid()).collection("reservation").document(info.getDate()).collection(info.getDate()).document(info.getTime()).set(info)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StartToast("예약에 성공 했습니다.");

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StartToast("예약에 실패했습니다");
                    }
                });

//        db.collection("enterprises").document(info.getCenterName() + info.getAddress()).collection("reservation").document(info.getDate()).collection(info.getDate()).document(info.getTime()).set(info)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        StartToast("예약에 성공 했습니다.");
//
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        StartToast("예약에 실패했습니다");
//                    }
//                });

}
    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
