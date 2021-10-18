package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private static Button requestButton;
    private static Button voteReqButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClickReqButtonListener();
        onClickVoteButtonListener();
    }

    public void onClickReqButtonListener(){
        requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".RequestSecretActivity");
                startActivity(intent);
            }
        });
    }

    public void onClickVoteButtonListener(){
        voteReqButton = (Button) findViewById(R.id.voteReqButton);
        voteReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".VotingActivity");
                startActivity(intent);
            }
        });
    }
}