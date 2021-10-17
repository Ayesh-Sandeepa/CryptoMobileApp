package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button voteButton=(Button) findViewById(R.id.voteButton);
        Button registerButton=(Button) findViewById(R.id.registerButton);
        Button loginButton=(Button) findViewById(R.id.loginButton);

        voteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), VotingActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), RequestSecretActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}