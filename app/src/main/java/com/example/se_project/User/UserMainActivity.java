package com.example.se_project.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.R;
import com.example.se_project.User.Search.SearchListViewAdapter;
import com.example.se_project.User.Search.SearchTitleClass;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class UserMainActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast terminate_guide_msg;
    SearchListViewAdapter adapter;
    ListView list;
    SearchView searchView;
    ArrayList<SearchTitleClass> arraylist = new ArrayList<SearchTitleClass>();
    java.util.HashMap<String,Object> HashMap = new HashMap<String,Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("brand").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    HashMap = (HashMap<String, Object>) documentSnapshot.getData();
                    String info = (String) HashMap.get("info");
                    String title = (String) HashMap.get("title");
                    SearchTitleClass stc = new SearchTitleClass(title, info);
                    arraylist.add(stc);
                    System.out.println("TEST STC : " + stc);
                }
                list = findViewById(R.id.listview);
                adapter = new SearchListViewAdapter(this,arraylist);
                list.setAdapter(adapter);

                // Locate the EditText in listview_main.xml
            }

        });

        StartActivity(UserMapActivity.class);


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

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        // 동일한 창이 여러번 뜨게 만드는 것이 아니라 기존에 켜져있던 창을 앞으로 끌어와주는 기능.
        // 이 플래그를 추가하지 않을 경우 창들이 중복돼서 계속 팝업되게 된다.
        // 메인화면을 띄우는 모든 코드에서 이 플래그를 추가해줘야 하는 것 같다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.SearchMenu);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("검색어를 입력하세요.");

        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
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
