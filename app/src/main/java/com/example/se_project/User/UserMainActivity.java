package com.example.se_project.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_project.LoginActivity;
import com.example.se_project.R;
import com.example.se_project.User.Search.SearchListViewAdapter;
import com.example.se_project.User.Search.SearchTitleClass;
import com.example.se_project.User.Search.UserSearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class UserMainActivity extends AppCompatActivity {
    final String[] selectOption = {"가평군", "고양시", "구리시", "김포시", "남양주시",
            "부천시", "성남시", "수원시", "시흥시", "안산시",
            "안양시", "양주시", "양평군", "여주시", "연천군시",
            "오산시", "용인시", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "하남시", "화성시"};
    private long backKeyPressedTime = 0;
    private int flag = 0;
    private Toast terminate_guide_msg;
    private String region;
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

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.LogoutMenu:
                FirebaseAuth.getInstance().signOut();
                StartActivity(LoginActivity.class);
                break;
            case R.id.SearchMenu:
                if(flag==1){
                    Log.d("TEST","test"+flag);
                    Intent intent = new Intent(this,UserSearchActivity.class);
                    intent.putExtra("region", region);
                    startActivity(intent);
                }
                else {
                    Log.d("TEST","test"+flag);
                    StartToast("지역을 선택해주세요.");
                }
                break;
            case R.id.SettingMenu:
                Toast.makeText(getApplicationContext(), "설정 메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Region:
                selectRegion(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void selectRegion(MenuItem item)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("지역을 선택하세요")
                .setItems(selectOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String RG = selectOption[i].toString();
                        item.setTitle(RG);
                        flag=1;
                        region =RG;
                    }
                })
                .setCancelable(true)
                .show();
    }
    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
