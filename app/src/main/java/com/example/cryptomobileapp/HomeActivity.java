package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
                requestData(url);

            }
        });
    }

    private void requestData(String url) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setUrl(url);

        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        downloader.execute(requestPackage);
    }

    private class Downloader extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected String doInBackground(RequestPackage... params) {
            Log.i("HTTP","Async task started");
            return HttpManager.getData(params[0]);
        }

        //The String that is returned in the doInBackground() method is sent to the
        // onPostExecute() method below. The String should contain JSON data.
        @Override
        protected void onPostExecute(String result) {
            try {
                //We need to convert the string in result to a JSONObject
                JSONObject jsonObject = new JSONObject(result);

                //The “ask” value below is a field in the JSON Object that was
                //retrieved from the BitcoinAverage API. It contains the current
                //bitcoin price
                String price = jsonObject.getString("status");

                //Now we can use the value in the mPriceTextView
                resultReceived.setText(price);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}