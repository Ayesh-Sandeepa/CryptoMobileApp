package com.example.cryptomobileapp;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;


public class RequestSecretActivity extends AppCompatActivity {

    Button buttonRequest;
    EditText enterNIC;
    TextView textSecretCheck,textSecretResend,textPhoneNumber;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_secret);

        buttonRequest = (Button)findViewById(R.id.buttonSubmitNIC);
        enterNIC = (EditText)findViewById(R.id.editTextNIC);
        textSecretCheck = (TextView)findViewById(R.id.textViewSecretCheck);
        textSecretResend = (TextView)findViewById(R.id.textViewSecretResend);
        textPhoneNumber = (TextView)findViewById(R.id.textViewPhoneNumber);

        textSecretCheck.setVisibility(View.GONE);
        textSecretResend.setVisibility(View.GONE);
        textPhoneNumber.setVisibility(View.GONE);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSecretCheck.setVisibility(View.VISIBLE);
                textSecretResend.setVisibility(View.VISIBLE);
                buttonRequest.setClickable(false);
                GetNumber();
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textSecretResend.setText( "Please Wait " + millisUntilFinished / 1000 + "s to Request a Resend");
                    }

                    public void onFinish() {
                        buttonRequest.setClickable(true);
                        buttonRequest.setText(R.string.resend);;
                    }
                }.start();
            }
        });
    }

    public void GetNumber() {

        if (ActivityCompat.checkSelfPermission(this, READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, READ_PHONE_NUMBERS) ==
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // Permission check

            // Create obj of TelephonyManager and ask for current telephone service
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = telephonyManager.getLine1Number();
            textPhoneNumber.setVisibility(View.VISIBLE);
            textPhoneNumber.setText(R.string.extracted_phone + phoneNumber);
            return;
        } else {
            // Ask for permission
            requestPermission();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{READ_SMS, READ_PHONE_NUMBERS, READ_PHONE_STATE}, 100);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, READ_SMS) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                String phoneNumber = telephonyManager.getLine1Number();
                textPhoneNumber.setVisibility(View.VISIBLE);
                textPhoneNumber.setText("Automatically Extracted Phone Number " + phoneNumber);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

}