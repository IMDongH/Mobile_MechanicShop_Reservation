package com.example.se_project.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.Center.CenterMainActivity;
import com.example.se_project.LoginActivity;
import com.example.se_project.User.UserMainActivity;
import com.example.se_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity  extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        startLoading();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if(user != null){
            // 로그인이 되어있다면 회원 정보가 등록됐는지 본다.
            String[] temp = {"users", "enterprises"};
            FirebaseFirestore fb = FirebaseFirestore.getInstance();
            for (String tempPath : temp) {
                DocumentReference documentReference = fb.collection(tempPath).document(user.getUid());
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document != null) {
                                if(document.exists()) {
                                    if (tempPath.equals("users"))
                                        StartActivity(UserMainActivity.class);
                                    else if (tempPath.equals("enterprises")) {
                                        Log.e("woong", "onComplete: " + user.getEmail());
                                        StartActivity(CenterMainActivity.class);
                                    } else {
                                        // 유저는 있는데 db가 없는 상황이 있다.
                                        // 회원탈퇴 기능이 추가되면서 간간히 발생한다.
                                        // 그런 상황을 위한 디펜시브 코드이다.
                                        Log.e("Unknown Error", "onComplete: login error");
                                        FirebaseAuth.getInstance().signOut();
                                        StartActivity(LoginActivity.class);
                                    }
                                }
                                else{
                                    StartActivity(LoginActivity.class);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("temp", "onFailure: null value");
                    }
                });
            }
        }
        else
            StartActivity(LoginActivity.class);
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
    }




}
