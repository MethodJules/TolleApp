package com.example.malek.tolleapp;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class EventApi extends AsyncTask {
    //this method is to parse the json
    private String jsonParse() throws IOException {
        final String url = "http://147.172.96.39/indeko/indekoapi/v1/node/";
        URL myUrl = new URL(url);
        HttpURLConnection myConnection = (HttpURLConnection)myUrl.openConnection();
        myConnection.setRequestMethod("GET");
       // myConnection.connect();
        InputStream inputStream = myConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
        StringBuffer buffer = new StringBuffer();
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line+"\n");
            Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

        }

        return buffer.toString();

    }

    @Override
    protected Object doInBackground(Object[] url) {
        try {
            jsonParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
