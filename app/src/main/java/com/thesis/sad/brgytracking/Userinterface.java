package com.thesis.sad.brgytracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Userinterface extends AppCompatActivity {

    private Button help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinterface);

        help= findViewById(R.id.btn_gethelp);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userinterface.this, Sample2Activity.class);
                startActivity(intent);
            }
        });

    }
}
