package com.example.cryptomobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class NumbersListAdapter extends RecyclerView.Adapter<NumbersListAdapter.ViewHolder>{

    ArrayList<Integer> nos=new ArrayList<Integer>();
    ArrayList<Integer> votedNumbers=new ArrayList<>();
    int noPerParty;

    public NumbersListAdapter(Context context){
        try{
            String json=null;

            InputStream is=context.getAssets().open("ballotPaper.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer, "UTF-8");

            JSONObject details=new JSONObject(json);
            noPerParty=details.getInt("noPerParty");

            for (int i = 0; i < noPerParty; i++) {
                nos.add(i+1);
            }
        }catch (IOException | JSONException ex){
            ex.printStackTrace();
        }

    }

    @NonNull
    @Override
    public NumbersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.list_numbers,parent,false);
        NumbersListAdapter.ViewHolder viewHolder=new NumbersListAdapter.ViewHolder(listItem);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersListAdapter.ViewHolder holder, int position) {
        holder.checkBox.setText(String.valueOf(nos.get(position)));
    }

    @Override
    public int getItemCount() {
        return noPerParty;
    }


    public ArrayList<Integer> getVotedNumbers(){
        return votedNumbers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CheckBox checkBox;

        public ViewHolder(View itemView){
            super(itemView);
            this.checkBox=(CheckBox) itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(ViewHolder.this.checkBox.isChecked()){
                votedNumbers.add(ViewHolder.this.getAdapterPosition());
            }else{
                votedNumbers.remove(ViewHolder.this.getAdapterPosition());
            }
        }
    }

}
