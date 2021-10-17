package com.example.cryptomobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class VotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

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

        MyListAdapter partyAdapter=new MyListAdapter(this);
        NumbersListAdapter numbersAdapter=new NumbersListAdapter(this);

        partyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        numbersRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        partyRecyclerView.setAdapter(partyAdapter);
        numbersRecyclerView.setAdapter(numbersAdapter);

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
}