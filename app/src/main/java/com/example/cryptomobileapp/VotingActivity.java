package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VotingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static Button voteSendButton;
    private ArrayList<Integer> votedNumbers;
    private int selectedParty;

    MyListAdapter partyAdapter;
    NumbersListAdapter numbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        onClickVoteButtonListener();

        TextView elec=findViewById(R.id.election);
        TextView ballot=findViewById(R.id.ballotID);

        try{
            JSONObject obj=new JSONObject(loadJSONFromAsset());
            String election=obj.getString("election");
            String ballotID=obj.getString("ballotID");
            elec.setText(election);
            ballot.setText(ballotID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView partyRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView numbersRecyclerView=(RecyclerView) findViewById(R.id.numbersView);

        partyAdapter=new MyListAdapter(this);
        numbersAdapter=new NumbersListAdapter(this);

        partyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        numbersRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        partyRecyclerView.setAdapter(partyAdapter);
        numbersRecyclerView.setAdapter(numbersAdapter);

        partyAdapter.setOnItemCLickListner(this);

    }

    public void onClickVoteButtonListener(){
        voteSendButton = (Button) findViewById(R.id.sendVoteButton);
        voteSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedParty=partyAdapter.getVotedParty();
                votedNumbers=numbersAdapter.getVotedNumbers();


                JSONObject postBody=new JSONObject();


            }
        });
    }

    public String loadJSONFromAsset(){
        String json=null;

        try{
            InputStream is=getAssets().open("ballotPaper.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer, "UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("HTTP","Click method ");
        Toast.makeText(this, String.valueOf(id),Toast.LENGTH_LONG).show();
    }
}