package com.thesis.sad.brgytracking;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thesis.sad.brgytracking.Model.information_of_user;

public class UserInformation extends AppCompatActivity {

    FloatingActionButton register;
    EditText firstname;
    EditText lastname;
    EditText age;
    EditText contact_number;
    EditText address;
    EditText guardian;
    EditText guardian_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        register = (FloatingActionButton)findViewById(R.id.floatingActionButton_register_user);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInformation.this, "Hello! there! ", Toast.LENGTH_SHORT).show();
                firstname = findViewById(R.id.editText_firstname);
                lastname = findViewById(R.id.editText_lastname);
                age = findViewById(R.id.editText_age);
                contact_number = findViewById(R.id.editText_contact_number);
                address = findViewById(R.id.editText_address);
                guardian = findViewById(R.id.editText_guardian);
                guardian_number = findViewById(R.id.editText_guardian_contact);

                final information_of_user user_information= new information_of_user(firstname.getText().toString(),lastname.getText().toString(),age.getText().toString(),contact_number.getText().toString(),address.getText().toString(),guardian.getText().toString(),guardian_number.getText().toString());




            }
        });
    }
}
