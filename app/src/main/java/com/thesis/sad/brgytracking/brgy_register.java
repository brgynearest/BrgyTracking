package com.thesis.sad.brgytracking;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thesis.sad.brgytracking.Model.brgy_login;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class brgy_register extends AppCompatActivity implements ConnectionCallbacks,OnConnectionFailedListener{
    private static final String TAG = "brgy_register";
    private static final int RequestPermissionCode = 1;
    private  GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;


    private TextView longitude;
    private TextView latitude;


    private Button btn_location;
    private EditText editext_username;
    private EditText edittext_password;
    private EditText edittext_contact;
    private EditText edittext_longitude;
    private EditText edittext_lattitude;
    private TextView signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brgy_register);

        longitude = findViewById(R.id.text_long);
        latitude = findViewById(R.id.text_lat);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        {
        }



        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("Users");
        signin = findViewById(R.id.sign_in);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(brgy_register.this, Login_Responder.class));
            }
        });

        btn_location = findViewById(R.id.btn_getlocation);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(brgy_register.this, "This is your location!", Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonregister = findViewById(R.id.btn_register);

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext_username = findViewById(R.id.text_brgyname);
                edittext_password = findViewById(R.id.text_password);
                edittext_contact = findViewById(R.id.text_contact);


                final brgy_login barangay = new brgy_login(editext_username.getText().toString(),edittext_password.getText().toString(),edittext_contact.getText().toString(),edittext_longitude.getText().toString(),edittext_lattitude.getText().toString());
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(barangay.getUsername()).exists())
                            Toast.makeText(brgy_register.this, "Already Exists!", Toast.LENGTH_SHORT).show();
                        else {
                            myRef.child(barangay.getUsername()).setValue(barangay);
                            Toast.makeText(brgy_register.this, "Registered!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            googleApiClient.connect();
        }catch (Exception e){
            Toast.makeText(this, "Onstart Error! ", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStop() {
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();

        }
        super.onStop();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
      if(ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
          requestPermissions();

      }else {
          fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
              @Override
              public void onSuccess(Location location) {
                  if(location != null){
                      Toast.makeText(brgy_register.this, "Location enabled!", Toast.LENGTH_SHORT).show();
                      edittext_longitude = findViewById(R.id.longitude);
                      edittext_lattitude = findViewById(R.id.lattitude);
                     /* String la= String.valueOf(location.getLatitude());
                      String lo = String.valueOf(location.getLatitude());*/
                      latitude.setText(String.valueOf(location.getLatitude()));
                      longitude.setText(String.valueOf(location.getLongitude()));
                      edittext_lattitude.setText(String.valueOf(location.getLatitude()));
                      edittext_longitude.setText(String.valueOf(location.getLongitude()));

                  }
                  else {
                      Toast.makeText(brgy_register.this, "Location not Found!" +
                              "", Toast.LENGTH_SHORT).show();

                  }
              }
          });
      }
    }

    private void requestPermissions() {
       ActivityCompat.requestPermissions(brgy_register.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RequestPermissionCode);
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e(TAG, "Connection Suspended!" );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onConnectionFailed: " + connectionResult.getErrorCode());

    }



}


