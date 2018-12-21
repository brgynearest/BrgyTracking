package com.thesis.sad.brgytracking;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class SampleActivity extends AppCompatActivity {

    private static final String TAG = "SampleActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        if(isServiceOK()){
            init();
        }
    }
    private void init(){
        Button btnregister = (Button) findViewById(R.id.btnmap);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(SampleActivity.this, Sample2Activity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServiceOK() {
        Log.d(TAG, "isServiceOK: checking google service version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SampleActivity.this);
        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServiceOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"isServiceOK: and error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SampleActivity.this, available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "We Can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
