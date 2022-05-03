package com.example.se_project.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.se_project.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
        db.collection("CarCenter").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    // latitude - 위도
                    // longitude - 경도
                    for (QueryDocumentSnapshot document:task.getResult()){
                        if (document.exists()){
                            String address = (String) document.getData().get("소재지도로명주소");
                            if (address != null &&address.contains("성남시")&&address.contains("수정구")){
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
}