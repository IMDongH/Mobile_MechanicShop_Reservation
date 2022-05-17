package com.example.se_project.User;

//import android.Manifest;
import android.Manifest;
import android.app.AlertDialog;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
//import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

//import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;

import com.example.se_project.LoginActivity;
import com.example.se_project.R;
import com.example.se_project.User.Search.SearchListViewAdapter;
import com.example.se_project.User.Search.SearchTitleClass;
import com.example.se_project.User.Search.UserSearchActivity;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.SettingsClient;
//import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.List;
//import java.util.Locale;

public class UserMainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    final String[] selectOption = {"가평군", "고양시", "구리시", "김포시", "남양주시",
            "부천시", "성남시", "수원시", "시흥시", "안산시",
            "안양시", "양주시", "양평군", "여주시", "연천군시",
            "오산시", "용인시", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "하남시", "화성시"};
    private long backKeyPressedTime = 0;
    private int flag = 0;
    private Toast terminate_guide_msg;
    private String region;
    private String TAG = "UserMainActivity : ";
    SearchListViewAdapter adapter;
    ListView list;
    SearchView searchView;
    ArrayList<SearchTitleClass> arraylist = new ArrayList<SearchTitleClass>();
    java.util.HashMap<String,Object> HashMap = new HashMap<String,Object>();
    ArrayList<String> regList = new ArrayList<>();
    HashMap<String, String> regTable = new HashMap<>();
    Geocoder geocoder;
    String address;
    String target;
    MenuItem regionMenu;
    LatLng curPo;

//    HashMap <String, String> regMap = new HashMap<>();
//     실제 GPS를 따오는데 사용되는 코드
//    private Marker currentMarker = null;
//    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
//    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
//    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초
//     onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
//    private static final int PERMISSIONS_REQUEST_CODE = 100;
//    boolean needRequest = false;
//     앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
//    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소
//
//    Location mCurrentLocatiion;
//    LatLng currentPosition;
//    String curAddress;
//    private FusedLocationProviderClient mFusedLocationClient;
//    private LocationRequest locationRequest;
//    private Location location;
//
//    private View mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_user_main);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

//        mLayout = findViewById(R.id.layout_user_main);

//        locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(UPDATE_INTERVAL_MS).setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        SettingsClient client = LocationServices.getSettingsClient(this);
//        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//            @Override
//            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                Log.d(TAG,"location request initialized");
//                locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(UPDATE_INTERVAL_MS).setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
//            }
//        }).addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if (e instanceof ResolvableApiException){
//                    try {
//                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        resolvable.startResolutionForResult(UserMainActivity.this, PERMISSIONS_REQUEST_CODE);
//                    }catch (IntentSender.SendIntentException sendEx){
//                        Log.d(TAG, sendEx.toString());
//                    }
//                }
//            }
//        });
//
//
//        builder.addLocationRequest(locationRequest);
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        geocoder = new Geocoder(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart");
//        if (checkPermission()) {
//            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
//            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//            if (mMap!=null)
//                mMap.setMyLocationEnabled(true);
//        }
//    }



//    @Override
//    protected void onStop() {
//
//        super.onStop();
//
//        if (mFusedLocationClient != null) {
//
//            Log.d(TAG, "onStop : call stopLocationUpdates");
//            mFusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//    }

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
        regionMenu = menu.findItem(R.id.Region);


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
                    startActivityForResult(intent, 1);
                }
                else {
                    Log.d("TEST","test"+flag);
                    StartToast("지역을 선택해주세요.");
                }
                break;
            case R.id.SettingMenu:
                Toast.makeText(getApplicationContext(), "설정 메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reservationCheck:
                StartActivity(UserReservationList.class);
                break;
            case R.id.Region:

                selectRegion(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String moveAddress = data.getStringExtra("address");
        List<Address> movelist = null;
        try {
            movelist = geocoder.getFromLocationName(moveAddress, 10);
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }

        LatLng newPo = new LatLng(movelist.get(0).getLatitude(), movelist.get(0).getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPo, 15));
    }

    public void selectRegion(MenuItem item) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("지역을 선택하세요")
                .setItems(selectOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String RG = selectOption[i].toString();
                        item.setTitle(RG);
                        flag=1;
                        region =RG;
//                        regMap = new HashMap<>();
                        regList = new ArrayList<>();
                        regTable = new HashMap<>();
                        mMap.clear();
                        db.collection(RG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    double cameraLat = 0.0;
                                    double camreaLon = 0.0;
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        if (document.exists()){
                                            String address = (String) document.getData().get("소재지도로명주소");
                                            double latitude = (double)document.getData().get("위도");
                                            double longitude = (double)document.getData().get("경도");
                                            if (cameraLat == 0)
                                                cameraLat = latitude;
                                            if (camreaLon == 0)
                                                camreaLon = longitude;


                                            LatLng location = new LatLng(latitude, longitude);
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(location);
                                            String centerName = (String) document.getData().get("자동차정비업체명");
                                            String phone;
                                            phone = (String) document.getData().get("phone");
                                            if (phone==null){
                                                phone = "미등록업체";
                                            }else{
                                                cameraLat = latitude;
                                                camreaLon = longitude;
                                                regList.add(centerName);
                                                regTable.put(centerName, phone);
                                            }

//                                            regMap.put(centerName, phone);
                                            markerOptions.title(centerName);
                                            markerOptions.snippet(address);

                                            mMap.addMarker(markerOptions);
                                            Log.d(TAG,"DocumentSnapshot data: "+document.getData()+"phone : "+phone);

                                        }
                                    }
                                    LatLng cameraLoc = new LatLng(cameraLat, camreaLon);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraLoc, 15));
                                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                        @Override
                                        public void onInfoWindowClick(@NonNull Marker marker) {
                                            Log.d(TAG,"Info window click :"+marker.getTitle());
                                            if (regList.contains(marker.getTitle())){
                                                Bundle data = new Bundle();
                                                data.putString("centerName",marker.getTitle());
                                                data.putString("centerAddress",marker.getSnippet());
                                                data.putString("phone",regTable.get(marker.getTitle()));
                                                Intent intent = new Intent(UserMainActivity.this, PopUpCenterInfo.class);
                                                intent.putExtras(data);
                                                startActivity(intent);
                                            }else {
                                                StartToast("미등록업체입니다. ");
                                            }

                                        }
                                    });
                                }
                            }
                        });



                    }
                })
                .setCancelable(true)
                .show();
    }

    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap){
        mMap = googleMap;
//        setDefaultLocation();

//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
//            Log.d(TAG,"should start location update");
//            startLocationUpdates();
//        }else{
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
//                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 권한이 필요합니다.",Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ActivityCompat.requestPermissions(UserMainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
//                    }
//                }).show();
//            }else{
//                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
//            }
//        }
//        HashMap <String, String> regMap = new HashMap<>();

        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        address = (String) document.getData().get("address");
                        if (address == null){
                            target = "성남시";
                        }else{
                            for (String temp : selectOption){
                                if (address.contains(temp)) {
                                    target = temp;
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < selectOption.length; i++){
                            if (target.equals(selectOption[i])) {
                                regionMenu.setTitle(selectOption[i]);
                                break;
                            }
                        }

                        db.collection(target).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    // latitude - 위도
                                    // longitude - 경도
                                    double cameraLat = 0.0;
                                    double camreaLon = 0.0;
                                    for (QueryDocumentSnapshot document:task.getResult()){
                                        if (document.exists()){
                                            String address = (String) document.getData().get("소재지도로명주소");
                                            double latitude = (double)document.getData().get("위도");
                                            double longitude = (double)document.getData().get("경도");
                                            if (cameraLat == 0)
                                                cameraLat = latitude;
                                            if (camreaLon == 0)
                                                camreaLon = longitude;

                                            LatLng location = new LatLng(latitude, longitude);
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(location);
                                            String centerName = (String) document.getData().get("자동차정비업체명");
                                            String phone = "";
                                            phone = (String) document.getData().get("phone");
                                            if (phone==null){
                                                phone = "미등록업체";
                                            }else{
                                                cameraLat = latitude;
                                                camreaLon = longitude;
                                                regList.add(centerName);
                                                regTable.put(centerName, phone);
                                            }
//                            regMap.put(centerName, phone);

                                            markerOptions.title(centerName);
                                            markerOptions.snippet(address);
                                            mMap.addMarker(markerOptions);
                                            Log.d(TAG,"DocumentSnapshot data: "+document.getData()+"phone :"+phone);

                                        }else{
                                            Log.d(TAG,"No document");
                                        }

                                    }
                                    if (address == null) {
                                        curPo = new LatLng(cameraLat, camreaLon);
                                    }else{
                                        List<Address> list = null;
                                        try {
                                            list = geocoder.getFromLocationName(address, 10);
                                        }catch (Exception e){
                                            Log.d(TAG, e.toString());
                                        }

                                        curPo = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());

                                    }


                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPo, 15));
                                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                        @Override
                                        public void onInfoWindowClick(@NonNull Marker marker) {
                                            Log.d(TAG,"Info window click :"+marker.getTitle());
//                            Log.e(TAG,regList.get(marker.getTitle()));
                                            if (regList.contains(marker.getTitle())){
                                                Bundle data = new Bundle();
                                                data.putString("centerName",marker.getTitle());
                                                data.putString("centerAddress",marker.getSnippet());
                                                data.putString("phone",regTable.get(marker.getTitle()));
                                                Intent intent = new Intent(UserMainActivity.this, PopUpCenterInfo.class);
                                                intent.putExtras(data);
                                                startActivity(intent);
                                            }else {
                                                StartToast("미등록업체입니다. ");
                                            }

                                        }
                                    });




                                }else{
                                    Log.d("TAG", "failed with", task.getException());
                                }
                            }
                        });

                    }
                }
            }
        });





    }

//    LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(@NonNull LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//            List<Location> locationList = locationResult.getLocations();
//
//            if (locationList.size() > 0 ) {
//                for (Location loc : locationList){
//                    Log.d(TAG,"loc - lat :"+loc.getLatitude()+", lon:"+loc.getLongitude());
//                }
//                location = locationList.get(locationList.size() - 1);
//                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
//
//                String markerTitle = "내 위치";
//                String markerSnippet = getCurrentAddress(currentPosition);
//
//                Log.d(TAG,"onLocationResult : "+markerSnippet);
//
//                setCurrentLocation(location, markerTitle, markerSnippet);
//
////                mCurrentLocatiion = location;
////                curAddress = markerSnippet;
//
//            }
//        }
//    };


//    public void startLocationUpdates(){
//        if (!checkLocationServicesStatus()) {
//            Log.d(TAG, "startLocationUpdates: call showDialogForLocationServiceSettings");
//            showDialogForLocationServiceSetting();
//        }else{
//            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//
//            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {
//
//                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
//                return;
//            }
//            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");
//
//            if (checkPermission()) {
//                mMap.setMyLocationEnabled(true);
//            }
//
//            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//
//
//        }
//    }


//    public String getCurrentAddress(LatLng latLng) {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//        List<Address> addresses;
//
//        try{
//            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
//
//
//        }catch (IOException ioException) {
//            Log.d(TAG,"지오코더 사용불가");
//            return "지오코더 사용불가";
//        }catch (IllegalArgumentException illegalArgumentException) {
//            Log.d(TAG, "잘못된 GPS");
//            return "잘못된 GPS 좌표";
//        }
//
//        if (addresses == null || addresses.size() == 0){
//            Log.d(TAG, "주소 미발견");
//            return "주소 미발견";
//        }else{
//            Address address = addresses.get(0);
//            return address.getAddressLine(0).toString();
//        }
//    }
//
//    public boolean checkLocationServicesStatus() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
//
//
//        if (currentMarker != null) currentMarker.remove();
//
//
//        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(currentLatLng);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//
//
//        currentMarker = mMap.addMarker(markerOptions);

//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
//        mMap.moveCamera(cameraUpdate);
//
//    }
//
//
//
//    public void setDefaultLocation() {
//
//        //디폴트 위치, Seoul
//        LatLng DEFAULT_LOCATION = new LatLng(37.4500, 127.1288);
//        String markerTitle = "위치정보 가져올 수 없음";
//        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";
//
//
//        if (currentMarker != null) {
//            currentMarker.remove();
//        }
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(DEFAULT_LOCATION);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        currentMarker = mMap.addMarker(markerOptions);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
//        mMap.moveCamera(cameraUpdate);
//
//    }
//
//
//    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
//    private boolean checkPermission() {
//
//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
//            return true;
//        }
//
//        return false;
//
//    }
//
//    /*
//     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
//     */
//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
//
//        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
//        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//
//            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
//
//            boolean check_result = true;
//
//
//            // 모든 퍼미션을 허용했는지 체크합니다.
//
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    check_result = false;
//                    break;
//                }
//            }
//
//
//            if (check_result) {
//
//                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
//                startLocationUpdates();
//            } else {
//                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
//                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
//
//
//                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
//                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
//                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View view) {
//
//                            finish();
//                        }
//                    }).show();
//
//                } else {
//
//
//                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
//                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
//                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View view) {
//
//                            finish();
//                        }
//                    }).show();
//                }
//            }
//
//        }
//    }
//
//    //여기부터는 GPS 활성화를 위한 메소드들
//    private void showDialogForLocationServiceSetting() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
//        builder.setTitle("위치 서비스 비활성화");
//        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
//                + "위치 설정을 수정하실래요?");
//        builder.setCancelable(true);
//        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                Intent callGPSSettingIntent
//                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
//            }
//        });
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        builder.create().show();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//
//            case GPS_ENABLE_REQUEST_CODE:
//
//                //사용자가 GPS 활성 시켰는지 검사
//                if (checkLocationServicesStatus()) {
//                    if (checkLocationServicesStatus()) {
//
//                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");
//
//
//                        needRequest = true;
//
//                        return;
//                    }
//                }
//
//                break;
//        }
//    }
}