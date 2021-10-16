package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RequestSecretActivity extends AppCompatActivity {

    Button buttonRequest,buttonResend ;
    EditText textNIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_secret);
    }
}