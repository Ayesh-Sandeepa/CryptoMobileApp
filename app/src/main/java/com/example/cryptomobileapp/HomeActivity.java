package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {
    private static Button requestButton;
    private static Button voteReqButton;

    private String url="192.168.1.104/posts/1";
    private TextView resultReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClickReqButtonListener();
        onClickVoteButtonListener();
        onClickHTTPButtonListener();

        resultReceived = (TextView) findViewById(R.id.restresult);
    }

    public void onClickReqButtonListener(){
        requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RequestSecretActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickVoteButtonListener(){
        voteReqButton = (Button) findViewById(R.id.voteReqButton);
        voteReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HTTP","Button clicked");
                Intent intent = new Intent(view.getContext(), VotingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickHTTPButtonListener(){
        voteReqButton = (Button) findViewById(R.id.button2);
        voteReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), LoginActivity.class);
                //startActivity(intent);
                Log.i("HTTP","Button clicked");
                try {
                    requestData(url);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void requestData(String url) throws ExecutionException, InterruptedException {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setUrl(url);

        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        String response=downloader.execute(requestPackage).get();
        Log.i("HTTP",response);

        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }







}



