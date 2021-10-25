package com.example.cryptomobileapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class VotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        TextView elec=findViewById(R.id.election);
        TextView ballot=findViewById(R.id.ballotID);
        TextView pk=findViewById(R.id.textViewPK);

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

        pk.setText(getPublicKey());

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

    public String getPublicKey(){
        try {
            InputStream is = getResources().openRawResource(R.raw.certificate);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String key =new String(buffer,"UTF-8");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate crt = cf.generateCertificate(new ByteArrayInputStream(buffer));
            PublicKey publickey = crt.getPublicKey();
            RSAPublicKey rsaPub  = (RSAPublicKey)(publickey);
            BigInteger modulus = rsaPub.getModulus();
            BigInteger exponent = rsaPub.getPublicExponent();
            String mod = exponent.toString();
            return mod;
        }catch (IOException ex){
            ex.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return "hgj";
    }
}