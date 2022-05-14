package com.example.se_project;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.Center.CenterInfoClass;
import com.example.se_project.Center.CenterMainActivity;
import com.example.se_project.Center.PopUpSearchCenterName;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class CenterSignupActivity extends AppCompatActivity {

    private static final String TAG = "카센터 회원가입 Activity";

    private FirebaseAuth mAuth;
    private FirebaseUser center_user;
    private FirebaseFirestore db;

    TextView center_region;
    TextView center_name;

    final String[] selectOption = {"가평군", "고양시", "구리시", "김포시", "남양주시",
            "부천시", "성남시", "수원시", "시흥시", "안산시",
            "안양시", "양주시", "양평군", "여주시", "연천군",
            "오산시", "용인시", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "하남시", "화성시"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        center_region = findViewById(R.id.center_region);
        center_name = findViewById(R.id.center_name);

        //지역선택, 카센터이름검색, 영업시작시간, 영업마감시간, 회원가입 버튼 이벤트
        findViewById(R.id.centerStartTime).setOnClickListener(onClickListener);
        findViewById(R.id.centerEndTime).setOnClickListener(onClickListener);
        findViewById(R.id.select_button).setOnClickListener(onClickListener);
        findViewById(R.id.search_button).setOnClickListener(onClickListener);
        findViewById(R.id.RegisterButton).setOnClickListener(onClickListener);

    }

    private void SignUp() {

        // EditText로 형 변환해준 이유는 일반 View는 getText 메서드를 사용할 수 없어서이다.
        String email = ((EditText) findViewById(R.id.EmailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.PasswordEditText)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.PasswordCheckEditText)).getText().toString();

        String name = ((EditText) findViewById(R.id.MemberInfoName)).getText().toString();
        String date = ((EditText) findViewById(R.id.MemberInfoDate)).getText().toString();
        String phone = ((EditText) findViewById(R.id.MemberInfoPhone)).getText().toString();

        String centerStart_time = ((EditText) findViewById(R.id.centerStartTime)).getText().toString();
        String centerEnd_time = ((EditText) findViewById(R.id.centerEndTime)).getText().toString();

        //이메일, 비번 확인
        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if (password.equals(passwordCheck)) {
                Log.d(TAG, "PASSWORD CK");

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    center_user = mAuth.getCurrentUser();
                                    //이름, 생일, 폰번호, 영업시간, 마감시간, 지역, 카센터이름 올바른지 확인
                                    if (name.length() > 0 && date.length() >= 6 && phone.length() >= 8
                                            && centerStart_time.contains("분")
                                            && centerEnd_time.contains("분")
                                            && center_region.getText().length() <=4
                                            && !center_name.getText().toString().contains("이름")) {
                                        //이름, 생일, 폰번호, 영업시간, 마감시간, 시/군, 카센터 이름을 넘겨줌
                                        //마지막 카센터 이름 검색을 위해 호출한다.
                                        SearchCenterName(name, date, phone,
                                                centerStart_time,
                                                centerEnd_time,
                                                center_name.getText().toString(),
                                                center_region.getText().toString());
                                    } else {
                                        CheckSignUpMemberInfoCondition(name, date, phone);
                                    }

                                } else {
                                    StartToast("회원가입에 실패하였습니다.");
                                }
                            }
                        });
            } else {
                // Toast 창 띄워주기
                StartToast("비밀번호가 일치하지 않습니다.");
            }
        } else {
            CheckSignUpCondition(email, password, passwordCheck);
        }
    }

    private void SearchCenterName(String name, String date, String phone,
                                  String StartTime, String EndTime,
                                  String center_name, String center_region) {



        // 시/군 컬렉션별 쿼리
        db.collection(center_region).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {

                            String CenterName = (String) document.getData().get("자동차정비업체명");
                            // 일치하는 카센터 이름 검사
                            if (CenterName.equals(center_name)) {
                                Log.e(TAG,center_name);

                                // 미리 넣어놨던 데이터에 전화번호 추가 (동혁 요청사항)
                                DocumentReference r = db.collection(center_region).document(document.getId());
                                r.update("phone",phone);

                                String RoadName_Address = (String) document.getData().get("소재지도로명주소");
                                double Longitude = (double) document.getData().get("경도");
                                double Latitude = (double) document.getData().get("위도");
                                long type = (long) document.getData().get("자동차정비업체종류");

                                CenterInfoClass center_Info = new CenterInfoClass(name, date, phone,
                                        StartTime, EndTime,
                                        CenterName, RoadName_Address,
                                        Longitude, Latitude, type);
                                dbInsertion(center_Info);
                                break;
                            }
                        }
                    }

                }
            }
        });
    }

    private void dbInsertion(CenterInfoClass center_Info) {

        db.collection("enterprises").document(center_Info.getCenterName() + center_Info.getRoadName_Address()).set(center_Info)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StartToast("회원가입에 성공하였습니다.");
                        StartActivity(CenterMainActivity.class);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StartToast("모든 조건이 만족되었지만 회원가입에 실패하였습니다.");
                        center_user.delete();
                    }
                });
    }

    //클릭이벤트들
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                // 회원가입 버튼
                case R.id.RegisterButton:
                    SignUp();
                    break;

                //영업 시작 시간 버튼
                case R.id.centerStartTime:

                    EditText centerStart_time = (EditText) findViewById(R.id.centerStartTime);

                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(CenterSignupActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String state = "AM";
                            // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                            if (selectedHour > 12) {
                                selectedHour -= 12;
                                state = "PM";
                            }
                            // EditText에 출력할 형식 지정
                            centerStart_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                        }
                    }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                    break;

                //영업 마감 시간 버튼
                case R.id.centerEndTime:

                    EditText centerEnd_time = (EditText) findViewById(R.id.centerEndTime);

                    Calendar mcurrentTime_endtime = Calendar.getInstance();
                    int hour_End = mcurrentTime_endtime.get(Calendar.HOUR_OF_DAY);
                    int minute_End = mcurrentTime_endtime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker_end;
                    mTimePicker_end = new TimePickerDialog(CenterSignupActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String state = "AM";
                            // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                            if (selectedHour > 12) {
                                selectedHour -= 12;
                                state = "PM";
                            }
                            // EditText에 출력할 형식 지정
                            centerEnd_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                        }
                    }, hour_End, minute_End, false); // true의 경우 24시간 형식의 TimePicker 출현
                    mTimePicker_end.setTitle("Select Time");
                    mTimePicker_end.show();
                    break;

                //지역 선택 버튼
                case R.id.select_button:
                    AlertDialog.Builder ad = new AlertDialog.Builder(CenterSignupActivity.this);
                    ad.setTitle("지역을 선택하세요")
                            .setItems(selectOption, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    String item = selectOption[i].toString();
                                    //센터 지역이름 텍스트뷰
                                    center_region.setText(item);
                                }
                            })
                            .setCancelable(true)
                            .show();
                    break;

                //정비소 이름 선택 버튼
                case R.id.search_button:
                    if(center_region.getText().toString().length()>=5){
                        StartToast("지역을 먼저 선택해주세요");
                        break;
                    }
                    // 카센터 이름을 가져오기 위해 팝업 activity 실행
                    Intent intent = new Intent(getApplicationContext(), PopUpSearchCenterName.class);
                    intent.putExtra("region", center_region.getText().toString());
                    startActivityForResult(intent,100);
                    break;
            }
        }
    };

    //팝업 activity로 가져온 카센터 이름을 처리한다
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if (data != null){
                String center_title = data.getStringExtra("name");
                center_name.setText(center_title);
            }
        }
    }

    private void CheckSignUpMemberInfoCondition(String name, String date, String phone) {
        // 메서드가 호출되는 시점에는 회원가입이 이루어진 상태이다.
        // 따라서 중간에 문제가 생겼다면 해당 계정을 삭제해주어야 한다.
        Log.e("temp", "CheckSignUpMemberInfoCondition: " + center_user.getEmail());
        center_user.delete();

        String centerStart_time = ((EditText) findViewById(R.id.centerStartTime)).getText().toString();
        String centerEnd_time = ((EditText) findViewById(R.id.centerEndTime)).getText().toString();

        if (name.length() <= 0) {
            StartToast("회원 이름의 길이를 확인해주세요 : 1자 이상");
        } else if (date.length() < 6) {
            StartToast("생년월일의 길이를 확인해주세요 : 6자 이상");
        } else if (phone.length() < 8) {
            StartToast("전화번호의 길이를 확인해주세요 : 8자 이상");
        } else if (center_region.getText().length() > 4) {
            StartToast("지역을 선택해주세요");
        }else if (center_name.getText().toString().contains("이름")) {
            StartToast("이름을 검색해주세요");
        }else if (centerStart_time.contains("시작")) {
            StartToast("영업 시작 시간을 선택해주세요");
        }else if (centerEnd_time.contains("마감")) {
            StartToast("영업 마감 시간을 선택해주세요");
        }
    }

    private void CheckSignUpCondition(String email, String password, String passwordCheck) {
        if (email.length() <= 0) {
            StartToast("이메일 길이를 확인해주세요 : 1자 이상");
        } else if (password.length() <= 0) {
            StartToast("비밀번호 길이를 확인해주세요 : 1자 이상");
        } else if (passwordCheck.length() <= 0) {
            StartToast("비밀번호 확인 문자를 확인해주세요 : 1자 이상");
        }
    }


    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        // 동일한 창이 여러번 뜨게 만드는 것이 아니라 기존에 켜져있던 창을 앞으로 끌어와주는 기능.
        // 이 플래그를 추가하지 않을 경우 창들이 중복돼서 계속 팝업되게 된다.
        // 메인화면을 띄우는 모든 코드에서 이 플래그를 추가해줘야 하는 것 같다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
