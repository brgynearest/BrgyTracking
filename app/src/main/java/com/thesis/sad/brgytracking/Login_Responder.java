package com.thesis.sad.brgytracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Login_Responder extends AppCompatActivity {

    private Button button_login;
    private static String Username;
    private EditText text_username;
    private EditText text_password;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__responder);
        TextView btn_register = (TextView) findViewById(R.id.sign_up);

        button_login = (Button)findViewById(R.id.btn_login);
        database = FirebaseDatabase.getInstance();

          myRef = database.getReference("Users");
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_username = findViewById(R.id.text_username);
                text_password = findViewById(R.id.text_password);
                Login(text_username.getText().toString(), text_password.getText().toString());


            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Responder.this,brgy_register.class));


            }
        });
    }

    public static String getUsername(){
        return Username;
    }

    private void Login(final String username, final String password) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        brgy_login login = dataSnapshot.child(username).getValue(brgy_login.class);
                        if(login.getPassword().equals(password)){
                            Toast.makeText(Login_Responder.this, "Success Login!", Toast.LENGTH_SHORT).show();
                            Username = text_username.getText().toString();
                            Intent i = new Intent(Login_Responder.this,BrgyMapInterface.class);
                            startActivity(i);
                            text_username.getText().clear();
                            text_password.getText().clear();
                        }else{
                            Toast.makeText(Login_Responder.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                            text_username.getText().clear();
                            text_password.getText().clear();

                        }
                    }

                }else {
                    Toast.makeText(Login_Responder.this, "Username is not registered", Toast.LENGTH_SHORT).show();
                    text_username.getText().clear();
                    text_password.getText().clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
