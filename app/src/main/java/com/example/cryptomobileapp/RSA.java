package com.example.cryptomobileapp;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSA {
    static BigInteger r;
    static BigInteger n;
    static BigInteger e;

    public Context context;
    public RSA(Context context){
        this.context=context;
    }

    public BigInteger[] getPublicKeyParameters(){
        try {
            InputStream is = context.getResources().openRawResource(R.raw.certificate);
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

    public BigInteger encrypt(String message, BigInteger[] publickeypara){
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
        BigInteger encryptedMessage = m.modPow(e, n);
        return encryptedMessage;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(BigInteger encMessage){
        BigInteger decryptedMessage = encMessage.modPow(e,n);
        byte[] bytes = Base64.getEncoder().encode(decryptedMessage.toByteArray());
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
