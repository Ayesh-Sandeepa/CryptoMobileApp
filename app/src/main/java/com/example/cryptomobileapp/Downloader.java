package com.example.cryptomobileapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Downloader extends AsyncTask<RequestPackage, String, String> {



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
            //resultReceived.setText(price);

            //con.getReslt()
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
