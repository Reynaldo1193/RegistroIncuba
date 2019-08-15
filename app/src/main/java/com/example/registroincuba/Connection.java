package com.example.registroincuba;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection extends AsyncTask <String,String,String> {

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection httpURLConnection = null;
        URL url = null;

        try {
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            int code = httpURLConnection.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                StringBuffer stringBuffer = new StringBuffer();
                while ((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                }

                return stringBuffer.toString();

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            Log.d("MYJSON","Todo bien");
        }else{
            Log.d("MYJSON","Todo mal. shit is null");
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

}
