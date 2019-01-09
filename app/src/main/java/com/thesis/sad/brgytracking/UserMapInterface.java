package com.thesis.sad.brgytracking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thesis.sad.brgytracking.Model.brgy_login;

import java.io.IOException;
import java.util.List;

public class UserMapInterface extends AppCompatActivity implements OnMapReadyCallback,LocationListener,GoogleMap.OnMarkerClickListener {

    private DatabaseReference mUsers;
    private GoogleMap mMap;
    Marker marker;
    private static final String TAG = "UserMapInterface";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int LOCATION_REQUEST_CODE= 101;
    private static final int REQEUST_LOCATION_PERMISSION = 1;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready!");
        mMap = googleMap;

        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s:dataSnapshot.getChildren()){
                    brgy_login brgy= s.getValue(brgy_login.class);
                    LatLng location = new LatLng(brgy.lattitude,brgy.longitude);
                    mMap.addMarker(new MarkerOptions().position(location).title(brgy.username)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private Boolean mLocationPermissionGranted = false;

    @Override
    public void onLocationChanged(Location location) {

    }
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        getLocationPermission();

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;
        mUsers=FirebaseDatabase.getInstance().getReference("Users");

       locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
       if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this, new String[]{
                   Manifest.permission.ACCESS_FINE_LOCATION},REQEUST_LOCATION_PERMISSION);
       }
       locationListener = new LocationListener() {
           @Override
           public void onLocationChanged(Location location) {
               double latitude = location.getLatitude();
               double longitude = location.getLongitude();
               LatLng latLng = new LatLng(latitude,longitude);

               if (marker != null){
                   marker.remove();
                   marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
               }
               else {
                   marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18.0f));
               }
           }

           @Override
           public void onStatusChanged(String provider, int status, Bundle extras) {

           }

           @Override
           public void onProviderEnabled(String provider) {

           }

           @Override
           public void onProviderDisabled(String provider) {

           }
       };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(UserMapInterface.this);
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionGranted=false;
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0)
                {
                    for(int i = 0; i < grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                           return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted ");
                    mLocationPermissionGranted=true;
                    initMap();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null){
            googleApiClient.connect();
        }
        else
        {

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }@Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
