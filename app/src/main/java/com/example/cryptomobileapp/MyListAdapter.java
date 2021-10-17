package com.example.cryptomobileapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    //private MyListData[] listdata;
    //JSONObject partyDetails;
    JSONArray partyDetails;

    public MyListAdapter(Context context){
        try{
            String json=null;

            InputStream is=context.getAssets().open("ballotPaper.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer, "UTF-8");

            JSONObject details=new JSONObject(json);
            partyDetails=details.getJSONArray("parties");
        }catch (IOException | JSONException ex){
            ex.printStackTrace();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.textView.setText(partyDetails.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return partyDetails.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            this.imageView=(ImageView) itemView.findViewById(R.id.imageView);
            this.textView=(TextView) itemView.findViewById(R.id.textView);

        }
    }

}
