package com.example.cryptomobileapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private int selectedPosition=-1;
    private RadioGroup radioGroup=null;
    private Context c;
    //JSONObject partyDetails;
    JSONArray partyDetails;
    JSONObject selectedParty;

    private AdapterView.OnItemClickListener onItemClickListener;

    public MyListAdapter(Context context){
        try{
            this.c=context;

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
        ViewHolder viewHolder=new ViewHolder(listItem, this);
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

    public int getVotedParty(){
        return selectedPosition;
    }

    public void setOnItemCLickListner(AdapterView.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public void onItemHolderClick(ViewHolder holder){
        if(onItemClickListener !=null){
            onItemClickListener.onItemClick(null,holder.itemView,holder.getAdapterPosition(),holder.getItemId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        public CheckBox checkBox;

        public MyListAdapter myListAdapter;

        public ViewHolder(View itemView, MyListAdapter myListAdapter){
            super(itemView);
            this.imageView=(ImageView) itemView.findViewById(R.id.imageView);
            this.textView=(TextView) itemView.findViewById(R.id.textView);
            this.checkBox=(CheckBox) itemView.findViewById(R.id.checkBox);

            this.myListAdapter=myListAdapter;

            checkBox.setOnClickListener(this);
         }

        @Override
        public void onClick(View v) {
            selectedPosition=getAdapterPosition();
            //myListAdapter.onItemHolderClick(ViewHolder.this);


        }
    }

}
