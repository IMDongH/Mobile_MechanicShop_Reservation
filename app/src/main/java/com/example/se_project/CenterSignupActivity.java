package com.example.se_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.Center.CenterInfoClass;
import com.example.se_project.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class CenterSignupActivity extends AppCompatActivity {

    private static final String TAG = "CenterSignupActivity";

    private FirebaseAuth mAuth;
    private FirebaseUser center_user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        findViewById(R.id.RegisterButton).setOnClickListener(onClickListener);
    }

    public void onStart() {
        super.onStart();
        center_user = mAuth.getCurrentUser();

        if (center_user != null) {
            center_user.reload();
        }
    }

    private void SignUp() {
        // EditText로 형 변환해준 이유는 일반 View는 getText 메서드를 사용할 수 없어서이다.
        String email = ((EditText) findViewById(R.id.EmailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.PasswordEditText)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.PasswordCheckEditText)).getText().toString();

        String name = ((EditText) findViewById(R.id.MemberInfoName)).getText().toString();
        String date = ((EditText) findViewById(R.id.MemberInfoDate)).getText().toString();
        String phone = ((EditText) findViewById(R.id.MemberInfoPhone)).getText().toString();

        String center_region = ((EditText) findViewById(R.id.center_region)).getText().toString();
        String center_name = ((EditText) findViewById(R.id.center_name)).getText().toString();

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
                                    //이름, 생일, 폰번호 확인
                                    if (name.length() > 0 && date.length() >= 6 && phone.length() >= 8) {
                                        SearchCenter(name, date, phone, center_name, center_region);
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

    private void SearchCenter(String name, String date, String phone, String center_name, String center_region) {

        db.collection("CarCenter").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {

                            String CenterName = (String) document.getData().get("자동차정비업체명");
                            String RoadName_Address = (String) document.getData().get("소재지도로명주소");

                            if (CenterName.equals(center_name)) { // (4,7) => 경기도 @@시 or @@군

                                double Longitude = (double) document.getData().get("경도");
                                double Latitude = (double) document.getData().get("위도");
                                double type
                                        = (double) document.getData().get("자동차정비업체종류");
                                CenterInfoClass center_Info = new CenterInfoClass(name, date, phone, CenterName, RoadName_Address, Longitude, Latitude, type);

                                Log.e(TAG, " 들어가나????? ");

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

        db.collection("enterprises").document(center_user.getUid()).set(center_Info)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StartToast("회원가입에 성공하였습니다.");
                        StartActivity(UserMainActivity.class);
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

    private void CheckSignUpMemberInfoCondition(String name, String date, String phone) {
        // 메서드가 호출되는 시점에는 회원가입이 이루어진 상태이다.
        // 따라서 중간에 문제가 생겼다면 해당 계정을 삭제해주어야 한다.
        Log.e("temp", "CheckSignUpMemberInfoCondition: " + center_user.getEmail());
        center_user.delete();

        if (name.length() <= 0) {
            StartToast("회원 이름의 길이를 확인해주세요 : 1자 이상");
        } else if (date.length() < 6) {
            StartToast("생년월일의 길이를 확인해주세요 : 6자 이상");
        } else if (phone.length() < 8) {
            StartToast("전화번호의 길이를 확인해주세요 : 8자 이상");
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // view에서 id를 받아오는데
            switch (view.getId()) {
                // id가 RegisterButton에서 받아온 아이디라면 :
                case R.id.RegisterButton:
                    SignUp();
                    break;
            }
        }
    };

    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
