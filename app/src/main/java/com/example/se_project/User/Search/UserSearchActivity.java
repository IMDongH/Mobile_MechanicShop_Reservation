package com.example.se_project.User.Search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class UserSearchActivity extends AppCompatActivity {
    ListView list;
    SearchListViewAdapter adapter;
    SearchView editsearch;
    ArrayList<SearchTitleClass> arraylist = new ArrayList<SearchTitleClass>();
    java.util.HashMap<String,Object> HashMap = new HashMap<String,Object>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        Intent intent = getIntent();
        String region = intent.getStringExtra("region");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup(region).get().addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                HashMap = (HashMap<String, Object>) documentSnapshot.getData();
                String name = (String) HashMap.get("자동차정비업체명");
                String Location = (String) HashMap.get("소재지도로명주소");
                SearchTitleClass stc = new SearchTitleClass(name, Location);
                arraylist.add(stc);
                System.out.println("TEST STC : " + stc);
            }
            list = findViewById(R.id.listview);
            adapter = new SearchListViewAdapter(this,arraylist);
            list.setAdapter(adapter);

            // Locate the EditText in listview_main.xml
        }

    });
    }
    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // 텍스트 입력 후 검색 버튼이 눌렸을 때의 이벤트
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            adapter.filter(newText);
            return false;
        }
    };

}
