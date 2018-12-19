package com.thesis.sad.brgytracking;


import android.content.Intent;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thesis.sad.brgytracking.Model.brgy_login;


public class brgy_register extends AppCompatActivity {
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

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("Users");
        signin = findViewById(R.id.sign_in);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(brgy_register.this, Login_Responder.class));
            }
        });

        btn_location = findViewById(R.id.btn_location);
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
                edittext_longitude = findViewById(R.id.longitude);
                edittext_lattitude = findViewById(R.id.lattitude);

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


       /* buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editext_username.getText().toString();
                String password = edittext_password.getText().toString();
                String contacts = edittext_contact.getText().toString();
                String longgi = edittext_longitude.getText().toString();
                String lat = editText_lattitude.getText().toString();

                if (!TextUtils.isEmpty(username)) {
                    String id = myRef.push().getKey();
                    brgy_login barangay = new brgy_login(id,username,password,contacts,longgi,lat);
                    myRef.child(id).setValue(barangay);
                    Toast.makeText(brgy_register.this, "Succesfully Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    Toast.makeText(brgy_register.this, "Unregistered", Toast.LENGTH_SHORT).show();
                    }



            }


        });*/

        }

}


