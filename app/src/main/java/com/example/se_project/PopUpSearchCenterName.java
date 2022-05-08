package com.example.se_project;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PopUpSearchCenterName extends Activity {

    private static final String TAG = "팝업 엑티비티";

    private FirebaseFirestore db;

    ListView listView;
    ListViewAdapter adapter;
    SearchView editsearch;
    ArrayList<CenterNameInfo> arraylist = new ArrayList<CenterNameInfo>();

    String result_CenterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE ); // 액션바 제거
        setContentView(R.layout.activity_search_center_name);

        db = FirebaseFirestore.getInstance();

        // 시/군 정보 받기
        Intent intent = getIntent();
        String region = intent.getStringExtra("region");

        //어뎁터에 해당 시/군에 있는 카센터 이름이 담긴 ArrayList 를 넘긴다
        db.collection(region).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {

                            String CenterName = (String) document.getData().get("자동차정비업체명");
                            CenterNameInfo data = new CenterNameInfo(CenterName);
                            arraylist.add(data);
                        }
                    }
                    listView = findViewById(R.id.listview);
                    adapter = new ListViewAdapter(getApplicationContext(),arraylist);
                    listView.setAdapter(adapter);

                    // 서치바 안에 텍스트가 바뀔 때
                    editsearch = findViewById(R.id.search);
                    editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            // 입력받은 문자열 처리

                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            // 입력란의 문자열이 바뀔 때 처리

                            adapter.filter(s);
                            return false;
                        }
                    });

                    //검색해서 나온 카센터 이름(리스트뷰) 선택 이벤트
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            result_CenterName = adapter.getItem(i).getCenterName();

                            Intent return_intent = new Intent();
                            return_intent.putExtra("name", result_CenterName);
                            setResult(RESULT_OK, return_intent);
                            finish();
                        }
                    });
                }
            }
        });

    }

}