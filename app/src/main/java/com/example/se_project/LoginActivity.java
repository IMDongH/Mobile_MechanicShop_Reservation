package com.example.se_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.Center.CenterMainActivity;
import com.example.se_project.User.UserMainActivity;
import com.example.se_project.R;
import com.example.se_project.init.Password_Init_Activity;
import com.example.se_project.init.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    EditText EmailEditText, PasswordEditText;
    CheckBox autoLogin;
    private RadioButton user, center;
    private RadioGroup radioGroup;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private long backKeyPressedTime = 0;
    private Toast terminate_guide_msg;

    public LoginActivity(Context context) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();
        EmailEditText = findViewById(R.id.EmailEditText);
        PasswordEditText = findViewById(R.id.PasswordEditText);
        findViewById(R.id.SignInButton).setOnClickListener(onClickListener);
        findViewById(R.id.CommonSignUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.FindPassWord).setOnClickListener(onClickListener);
        user = (RadioButton) findViewById(R.id.user);
        center = (RadioButton) findViewById(R.id.center);
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }
    private void StartToast(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public String SignIn(String email, String password) {
//        String email = ((EditText) findViewById(R.id.EmailEditText)).getText().toString();
//        String password = ((EditText) findViewById(R.id.PasswordEditText)).getText().toString();
        if (email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                firebaseUser = mAuth.getCurrentUser();
                                db = FirebaseFirestore.getInstance();
                                CheckUser(firebaseUser, db);
                            } else {
                                StartToast("로그인 실패 : 로그인 정보가 일치하지 않습니다.");

                            }
                        }
                    });
            return "성공";
        } else {
            StartToast("빈칸을 확인해주세요.");
            return "오류";
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // view에서 id를 받아오는데
            switch (view.getId()) {
                case R.id.SignInButton:
                    String email = ((EditText) findViewById(R.id.EmailEditText)).getText().toString();
                    String password = ((EditText) findViewById(R.id.PasswordEditText)).getText().toString();
                    SignIn(email,password);
                    break;
//                case R.id.ToPasswordInitButton:
//                    StartActivity(Password_Init_Activity.class);
                case R.id.CommonSignUpButton:
                    StartActivity(SignUpActivity.class);
                    break;
                case R.id.FindPassWord:
                    StartActivity(Password_Init_Activity.class);
                    break;

            }
        }
    };

    public void CheckUser(FirebaseUser firebaseUser, FirebaseFirestore db) {
        String[] temp = {"users", "enterprises"};
        if(user.isChecked()==false&&center.isChecked()==false)
        {
            StartToast("로그인 유형을 선택해주세요.");
            return ;
        }
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        for (String tempPath : temp) {
            // 문제1 : 멤버 정보가 없다면 로그인이 안된다.
            DocumentReference documentReference = fb.collection(tempPath).document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null)
                            if (document.exists()) {
                                if (tempPath.equals("users")&&user.isChecked()) {
                                    StartActivity(UserMainActivity.class);
                                } else if (tempPath.equals("enterprises")&&center.isChecked()) {
                                    StartActivity(CenterMainActivity.class);
                                }

                            }
                    }
                }
            });
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // 로그인화면에서 뒤로가기를 누르면 종료된다.
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

    }
}