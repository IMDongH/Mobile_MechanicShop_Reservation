package com.example.se_project.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.se_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.commons.lang3.ObjectUtils;

public class UserMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FirebaseFirestore db;
//    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        db = FirebaseFirestore.getInstance();
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap){
        mMap = googleMap;
        db.collection("성남시").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    // latitude - 위도
                    // longitude - 경도
                    for (QueryDocumentSnapshot document:task.getResult()){
                        if (document.exists()){
                            String address = (String) document.getData().get("소재지도로명주소");
                            if (address != null &&address.contains("수정구")){
                                double latitude = (double)document.getData().get("위도");
                                double longitude = (double)document.getData().get("경도");

                                LatLng location = new LatLng(latitude, longitude);
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(location);
                                String centerName = (String) document.getData().get("자동차정비업체명");
                                markerOptions.title(centerName);
                                markerOptions.snippet(address);
                                mMap.addMarker(markerOptions);
                                Log.d("TAG","DocumentSnapshot data: "+document.getData());
                            }
                        }else{
                            Log.d("TAG","No document");
                        }

                    }
                    double gachonLat = 37.4500;
                    double gachonLon = 127.1288;
                    LatLng gachon = new LatLng(gachonLat, gachonLon);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gachon, 15));
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            AlertDialog.Builder ad = new AlertDialog.Builder(UserMapActivity.this);
                            ad.setIcon(R.mipmap.ic_launcher);
                            ad.setTitle(marker.getTitle());
                            ad.setMessage(marker.getSnippet());
                            ad.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("TAG","reserve pressed");
                                    Bundle dataBundle = new Bundle();
                                    dataBundle.putString("centerName",marker.getTitle());
                                    dataBundle.putString("centerAddress",marker.getSnippet());
                                    Intent intent = new Intent(getApplicationContext(),UserCarCenterReservationActivity.class);
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            });

                            ad.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("TAG","Close button pressed");
                                    dialogInterface.dismiss();
                                }
                            });
                            ad.show();

                            return false;
                        }
                    });

//                    try{
//                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//                                if (location != null){
//                                    double curLat = location.getLatitude();
//                                    double curLon = location.getLongitude();
//                                    LatLng curLocation = new LatLng(curLat, curLon);
//                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 10));
//                                }
//                            }
//                        });
//                    }catch (SecurityException e){
//                        Log.d("GPS error", "cant set cur location");
//                    }

                }else{
                    Log.d("TAG", "failed with", task.getException());
                }
            }
        });


    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        // 동일한 창이 여러번 뜨게 만드는 것이 아니라 기존에 켜져있던 창을 앞으로 끌어와주는 기능.
        // 이 플래그를 추가하지 않을 경우 창들이 중복돼서 계속 팝업되게 된다.
        // 메인화면을 띄우는 모든 코드에서 이 플래그를 추가해줘야 하는 것 같다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}