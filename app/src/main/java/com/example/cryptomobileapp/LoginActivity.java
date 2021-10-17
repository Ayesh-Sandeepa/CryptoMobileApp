package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    Button buttonSecret,buttonOTP,buttonOTPResend;
    EditText textSecret,textOTP;

    TextView labelOTP,labelOTPResend,labelOTPSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSecret = (Button)findViewById(R.id.buttonSecretSubmit);
        textSecret = (EditText)findViewById(R.id.editTextSecret);

        buttonOTP = (Button)findViewById(R.id.buttonSubmitOTP);
        buttonOTPResend = (Button)findViewById(R.id.buttonResendOTP);
        textOTP = (EditText)findViewById(R.id.editTextOTP);

        labelOTP = (TextView)findViewById(R.id.textViewOTP);
        labelOTPResend = (TextView)findViewById(R.id.textViewResendOTP);
        labelOTPSent = (TextView)findViewById(R.id.textViewOTPsent);

        textOTP.setVisibility(View.GONE);
        buttonOTP.setVisibility(View.GONE);
        labelOTP.setVisibility(View.GONE);
        labelOTPResend.setVisibility(View.GONE);
        labelOTPSent.setVisibility(View.GONE);
        buttonOTPResend.setVisibility(View.GONE);

        buttonSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textOTP.setVisibility(View.VISIBLE);
                buttonOTP.setVisibility(View.VISIBLE);
                labelOTP.setVisibility(View.VISIBLE);
                labelOTPSent.setVisibility(View.VISIBLE);
                labelOTPResend.setVisibility(View.VISIBLE);

                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        labelOTPResend.setText( "Please Wait " + millisUntilFinished / 1000 + "s to Request a Resend");
                    }

                    public void onFinish() {
                        buttonOTPResend.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });

        buttonOTPResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        labelOTPResend.setText( "Please Wait " + millisUntilFinished / 1000 + "s to Request a Resend");
                        buttonOTPResend.setClickable(false);
                    }

                    public void onFinish() {
                        buttonOTPResend.setClickable(true);
                    }
                }.start();
            }
        });

        buttonOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}