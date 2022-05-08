package com.example.se_project.User.Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
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
    String TAG = "SEARCH : ";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("검색");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String region = intent.getStringExtra("region");
        Log.d(TAG,region);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup(region).get().addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                HashMap = (HashMap<String, Object>) documentSnapshot.getData();
                String name = (String) HashMap.get("자동차정비업체명");
                String Location = (String) HashMap.get("소재지도로명주소");
                SearchTitleClass stc = new SearchTitleClass(name, Location);
                arraylist.add(stc);
                Log.d(TAG,name + Location);
            }
            list = findViewById(R.id.listview);
            adapter = new SearchListViewAdapter(this,arraylist);
            list.setAdapter(adapter);

            // Locate the EditText in listview_main.xml
            editsearch = findViewById(R.id.search);
            editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.filter(s);
                    return false;
                }
            });
        }
        else
        {
            Log.d(TAG,"FAIL");
        }

    });
    }


}
