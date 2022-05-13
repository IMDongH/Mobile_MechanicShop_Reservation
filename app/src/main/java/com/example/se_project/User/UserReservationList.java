package com.example.se_project.User;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;
import com.example.se_project.User.Reservation.ReservationListClass;
import com.example.se_project.User.Reservation.ReservationListViewAdapter;
import com.example.se_project.User.Search.SearchListViewAdapter;
import com.example.se_project.User.Search.SearchTitleClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserReservationList extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    TextView reservationDay;
    ReservationListViewAdapter adapter;
    ListView list;
    private String TAG = "reservation list";
    CalendarView calendarView;
    java.util.HashMap<String,Object> HashMap = new HashMap<String,Object>();
    ArrayList<ReservationListClass> arraylist = new ArrayList<ReservationListClass>();
    private String Date;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_list);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("예약 현황");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date day = new Date();
        reservationDay = findViewById(R.id.reservationDay);

        Date = simpleDateFormat.format(day);
        list = findViewById(R.id.reservationList);
        System.out.println("DATE"+Date);
        reservationListCheck(Date);
        reservationDay.setText(Date);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
//                complete_Btn.setVisibility(View.VISIBLE);
                Date = checkDay(year, month, dayOfMonth);
                reservationDay.setText(Date);
                reservationListCheck(Date);
                System.out.println(Date);
            }
        });
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
    public String checkDay(int cYear, int cMonth, int cDay)
    {
        String month;
        String day;
        cMonth=cMonth+1;
        cDay=cDay;
        if(cMonth<10)
        {
            month = "0"+Integer.toString(cMonth);
        }
        else
        {
            month = Integer.toString(cMonth);
        }
        if(cDay<10)
        {
            day = "0"+Integer.toString(cDay);
        }
        else
        {
            day = Integer.toString(cDay);
        }
        String date = cYear + "-" + month + "" + "-" + day;

        return date;
    }

    public void reservationListCheck(String date)
    {
        db.collection("users").document(user.getUid()).collection("reservation").document(date).collection(date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                arraylist.clear();
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                HashMap = (HashMap<String, Object>) documentSnapshot.getData();
                                String date = (String) HashMap.get("time");
                                String name = (String) HashMap.get("centerName");
                                String Location = (String) HashMap.get("address");
                                ReservationListClass stc = new ReservationListClass(date,name, Location);
                                arraylist.add(stc);
                                Log.d(TAG, name + Location);

                            }

                    adapter = new ReservationListViewAdapter(getApplicationContext(), arraylist);
                    list.setAdapter(adapter);
                    adapter.addItem();
                        } else {
//
                            Log.d(TAG, "FAIL");
                        }
                    }
                });
    }

}
