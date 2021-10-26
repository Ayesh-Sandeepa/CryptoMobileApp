package com.example.cryptomobileapp;
import java.util.Base64;
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
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;


public class VotingActivity extends AppCompatActivity {

    static BigInteger r;
    static BigInteger n;
    static BigInteger e;

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

        pk.setText(blind("a", (getPublicKeyParameters())).toString());

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

    public BigInteger[] getPublicKeyParameters(){
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
            return new BigInteger[]{modulus, exponent};
        }catch (IOException ex){
            ex.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger blind(String message, BigInteger[] publickeypara){
        n = publickeypara[0]; // modulus
        e = publickeypara[1]; // exponent
        byte[] msg = new byte[0];  // if want hash the message and concert to byte
        try {
            msg = message.getBytes("UTF8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        BigInteger m = new BigInteger(msg);
        SecureRandom random = null;
        random = new SecureRandom();
        byte[] randomBytes = new byte[10];
        random.nextBytes(randomBytes);
        r = new BigInteger(randomBytes);
        BigInteger blindedMsg = ((r.modPow(e, n)).multiply(m)).mod(n);
        return blindedMsg;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String unblind(BigInteger blidedSignature){
        BigInteger s = r.modInverse(n).multiply(blidedSignature).mod(n);
        byte[] bytes = Base64.getEncoder().encode(s.toByteArray());
        //byte[] bytes = new Base64().encode(s.toByteArray());
        String signature = (new String(bytes));
        return signature;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean verify(String signature,String originalMsg, BigInteger[] publickeypara){
        byte[] bytes = signature.getBytes();
        byte[] decodedBytes = Base64.getDecoder().decode(bytes);
        BigInteger sig = new BigInteger(decodedBytes);
        n = publickeypara[0]; // modulus
        e = publickeypara[1]; // exponent
        BigInteger extractedByteMsg = sig.modPow(e, n);
        String extractedMessage = new String(extractedByteMsg.toByteArray());
        if(extractedMessage == originalMsg){
            return true;
        }
        else{
            return false;
        }
    }
}