package com.example.se_project.Center;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.LoginActivity;
import com.example.se_project.R;
import com.example.se_project.User.Search.UserSearchActivity;
import com.example.se_project.User.UserReservationList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CenterMainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private Toast terminate_guide_msg;

    final String Tag = "카센터 메인";

    Calendar myCalendar = Calendar.getInstance();
    String selected_Date;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    ListView listView;
    TextView title;
    ArrayList<Reservation_Info> Datalist;
    String center_name;
    ReservationAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("예약관리");

        // 파베
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        title = findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.listview);
        Datalist = new ArrayList<Reservation_Info>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteDialog(position);
            }
        });
        // 처음 로그인 했을 때
        db.collection("enterprises").document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            // 로그인시 카센터 이름 가져옴
                            center_name = (String) document.getData().get("centerName");
                            //Log.e(Tag, center_name);
                            title.setText(center_name);
                            // 로그인시 오늘 날짜 보여주기
                            selected_Date = myCalendar.get(Calendar.YEAR) + "-" + "0" +(myCalendar.get(Calendar.MONTH) + 1) + "-" + myCalendar.get(Calendar.DAY_OF_MONTH);
                            Log.e(Tag, selected_Date);
                        }

                        //화면에 리스트 뷰 뿌리기
                        scatter();
                    }
                });

    }

    public void scatter() {
        // 리스트뷰에 넘길 data 가져와서 adapter 에 넘기기
        db.collection("reservation").document(center_name).collection(selected_Date).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Log.e(Tag, selected_Date);

                        if (task.isSuccessful()) {
                            // 리스트 비워주기
                            Datalist.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    String name = (String) document.getData().get("name");
                                    String centerName = (String) document.getData().get("centerName");
                                    String phone = (String) document.getData().get("phone");
                                    String time = (String) document.getData().get("time");
                                    String type = (String) document.getData().get("carType");
                                    String why = (String) document.getData().get("content");
                                    String UID = (String) document.getData().get("userId");
                                    Reservation_Info data = new Reservation_Info(name, phone, time, type, why, selected_Date,UID);
                                    Datalist.add(data);
                                }
                            }


                            myAdapter = new ReservationAdapter(CenterMainActivity.this, Datalist);
                            listView.setAdapter(myAdapter);
                        }
                    }
                });
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
        if (true) {
            String myFormat = "yyyy-MM-dd";    // 출력형식   2022/05/12
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

            selected_Date = sdf.format(myCalendar.getTime());
        }
        //날짜 바뀔 때 리스트 뷰 갱신하기
        scatter();
    }


    // 로그아웃, 날짜 선택
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.LogoutMenu:
                FirebaseAuth.getInstance().signOut();
                StartActivity(LoginActivity.class);
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

    }
    private void deleteDialog(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme));
        builder.setTitle("예약 취소"); builder.setMessage(Datalist.get(position).getName()+"의 예약을 취소하시겠습니까?");
        builder.setNegativeButton("예", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                db.collection("users").document(Datalist.get(position).getUID()).collection("reservation")
                        .document(Datalist.get(position).getDate()).collection(Datalist.get(position).getDate()).document(Datalist.get(position).getTime()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("TET"+center_name);
                        System.out.println("TT"+ Datalist.get(position).getTime());
                        System.out.println("DT"+ Datalist.get(position).getDate());
                        db.collection("reservation").document(center_name).collection(Datalist.get(position).getDate())
                                .document(Datalist.get(position).getTime()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                StartToast(position + "예");
                                Datalist.remove(position);
                                myAdapter.deleteItem(position);
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TEST", "Error deleting document", e);
                                    }
                                });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TEST", "Error deleting document", e);
                            }
                        });

            }
        });

        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
    private void StartToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}