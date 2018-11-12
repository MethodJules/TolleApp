package com.example.malek.tolleapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private Button button;
    private RequestQueue mQueue;
    public CalendarView mCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //When clicked This button fetches the events with the API
        button = findViewById(R.id.synchroniseButton);
        mQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventApi a = new EventApi();
                Object[] obg = new Object[1];
                a.doInBackground(obg);
            }
        });

        mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //the date is as follow mm/dd/yyyy
                    String date = (i1+1) + "/" + i2 +"/" + i;
                Log.d(TAG, "onSelectedDayChange: Date"+ date);
                Intent intent = new Intent(CalendarActivity.this,AppointmentActivity.class);
                //packs the extra content with a key "keyDate" and the date value
                intent.putExtra("keyDate",date);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.backToMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainInDekoActivity();
            }
        });
    }



    private void tralala()
    {
        final String url = "http://147.172.96.39/indeko/indekoapi/v1/node/";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("here", "hello");
                            JSONArray jsonArray = response.getJSONArray("events");
                            final String EVENT = "event";
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject events = jsonArray.getJSONObject(i);
                                if (events.getString("type").equals(EVENT)){
                                    String final_url = url + events.getString("nid");

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
    //this method is set to the button @zurück to go back to the home Page of InDeko
    public void openMainInDekoActivity(){
        Intent intent = new Intent(this, MainInDekoActivity.class);
        startActivity(intent);
    }

    //this method is set to the button @Termin to go to the appointment activity
    public void openAppointmentActivity(){
        Intent intent = new Intent(this, AppointmentActivity.class);
        startActivity(intent);
    }



}
