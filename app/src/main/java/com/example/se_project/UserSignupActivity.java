package com.example.se_project;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;
import com.example.se_project.User.UserInfoClass;
import com.example.se_project.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserSignupActivity  extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private long backKeyPressedTime = 0;
    private Toast terminate_guide_msg;
    private static final String TAG = "SignUpActivity";
    private Geocoder geocoder;

    public UserSignupActivity(Context context) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("사용자 회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.RegisterButton).setOnClickListener(onClickListener);
    }
    public void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if(user != null) {
            user.reload();
        }
    }
    private void SignUp() {
        // EditText로 형 변환해준 이유는 일반 View는 getText 메서드를 사용할 수 없어서이다.
        String email = ((EditText)findViewById(R.id.EmailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.PasswordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.PasswordCheckEditText)).getText().toString();

        String name = ((EditText)findViewById(R.id.MemberInfoName)).getText().toString();
        String date = ((EditText)findViewById(R.id.MemberInfoDate)).getText().toString();
        String phone = ((EditText)findViewById(R.id.MemberInfoPhone)).getText().toString();
        String address = ((EditText)findViewById(R.id.MemberInfoAddress)).getText().toString();

        geocoder = new Geocoder(this);
        List<Address> list = null;
        try{
            list = geocoder.getFromLocationName(address, 10);
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
        if (list != null){
            if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0 && list.size() > 0) {
                if (password.equals(passwordCheck)) {
                    Log.d(TAG,"PASSWORD CK");
                    Log.e(TAG,list.get(0).getAddressLine(0));
                    Log.e(TAG,list.get(0).getAdminArea());
//                    Log.e(TAG,list.get(0).getSubAdminArea());
                    final String curAddress = list.get(0).getAddressLine(0);
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "createUserWithEmail:success");
                                        user = mAuth.getCurrentUser();
                                        dbInsertion(name,  date, phone, curAddress);

                                    } else {

                                        StartToast("회원가입에 실패하였습니다.");
                                    }
                                }
                            });
                } else {
                    // Toast 창 띄워주기
                    StartToast("비밀번호가 일치하지 않습니다.");
                }
            }
            else {
                CheckSignUpCondition(email, password, passwordCheck, address);
            }
        }


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
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void dbInsertion(String name, String date, String phone, String address) {


        if(name.length() > 0 && date.length() >= 6 && phone.length() >= 8) {
            UserInfoClass userInfo = new UserInfoClass(name, phone, date, address);
            db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getUid()).set(userInfo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            StartToast("회원가입에 성공하였습니다.");

                                StartActivity(UserMainActivity.class);
                                finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    StartToast("모든 조건이 만족되었지만 회원가입에 실패하였습니다.");
                    user.delete();
                }
            });
        } else {
            CheckSignUpMemberInfoCondition(name, date, phone);
        }
    }
    private String CheckSignUpMemberInfoCondition(String name, String date, String phone) {
        // 메서드가 호출되는 시점에는 회원가입이 이루어진 상태이다.
        // 따라서 중간에 문제가 생겼다면 해당 계정을 삭제해주어야 한다.
        Log.e("temp", "CheckSignUpMemberInfoCondition: " + user.getEmail());
        user.delete();

            if(name.length() <= 0) {
                StartToast("회원 이름의 길이를 확인해주세요 : 1자 이상");
                return "회원 이름 오류";
            }
            else if(date.length() < 6) {
                StartToast("생년월일의 길이를 확인해주세요 : 6자 이상");
                return "생년월일 오류";
            }
            else if(phone.length() < 8) {
                StartToast("전화번호의 길이를 확인해주세요 : 8자 이상");
                return "전화번호 오류";
            }
            else{
                return "조건 만족";
            }
    }

    public String CheckSignUpCondition(String email, String password, String passwordCheck, String address) {
        if(email.length() <= 0) {
            StartToast("이메일 길이를 확인해주세요 : 1자 이상");
            return "이메일 오류";
        }
        else if(password.length() <= 0) {
            StartToast("비밀번호 길이를 확인해주세요 : 1자 이상");
            return "비밀번호 오류";
        }
        else if(passwordCheck.length() <= 0) {
            StartToast("비밀번호 확인 문자를 확인해주세요 : 1자 이상");
            return "비밀번호 확인 오류";
        }else if(address.length() <=0)
            {
            StartToast("주소를 확인해주세요");
            return "주소 오류";
        }
        else
        {
            return "조건 만족";
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
