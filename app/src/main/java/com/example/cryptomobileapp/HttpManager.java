package com.example.cryptomobileapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    public static String getData(RequestPackage requestPackage) {

        Log.i("HTTP", "Http manager get data executed");

        BufferedReader reader = null;
        String uri = requestPackage.getUrl();
        Log.i("HTTP", uri);

        if (requestPackage.getMethod().equals("GET")) {
            uri += "?" + requestPackage.getEncodedParams();
            //As mentioned before, this only executes if the request method has been
            //set to GET
            Log.i("HTTP","Params successfully added to url");
        }

        try {

            /*JSONObject kson=new JSONObject();
            kson.put("name","Election");
            JSONArray pt=new JSONArray();
            pt.put("Ayesh");
            pt.put("Sandeepa");
            kson.put("students",pt);*/

            Log.i("HTTP", uri);
            URL url = new URL(uri);
            Log.i("HTTP", uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.i("HTTP","Connection Established");
            con.setRequestMethod(requestPackage.getMethod());

            if (requestPackage.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter writer =
                        new OutputStreamWriter(con.getOutputStream());
                writer.write(requestPackage.getBody().toString());
                writer.flush();
            }

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            Log.i("HTTP","Got data from server");
            Log.i("HTTP",sb.toString());
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
