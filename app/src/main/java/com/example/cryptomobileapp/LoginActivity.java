package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    Button buttonSecret,buttonOTP ;
    EditText textSecret,textOTP;

    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSecret = (Button)findViewById(R.id.button);
        textSecret = (EditText)findViewById(R.id.editTextTextPassword);

        buttonOTP = (Button)findViewById(R.id.button2);
        textOTP = (EditText)findViewById(R.id.editTextTextPassword2);


        tx1 = (TextView)findViewById(R.id.textView2);

        textOTP.setVisibility(View.GONE);
        buttonOTP.setVisibility(View.GONE);
        tx1.setVisibility(View.GONE);

        buttonSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textOTP.setVisibility(View.VISIBLE);
                buttonOTP.setVisibility(View.VISIBLE);
                tx1.setVisibility(View.VISIBLE);

            }
        });
    }
}