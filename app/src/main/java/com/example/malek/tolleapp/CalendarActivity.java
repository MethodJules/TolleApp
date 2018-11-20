package com.example.malek.tolleapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private Button button;
    public CalendarView mCalendarView;
    private TextView mTextViewResult;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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

        mTextViewResult = findViewById(R.id.resultView);
        mQueue = Volley.newRequestQueue(this);
        button = findViewById(R.id.button_parse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){
        final String URL_HOME = "http://147.172.96.39/indeko/indekoapi/v1/node";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_HOME, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray jsonArray){


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject events = null;
                                try {
                                    events = jsonArray.getJSONObject(i);
                                    if (events.getString("type").equals("event")){
                                        String id = events.getString("nid");
                                       String URL = URL_HOME +"/" + id;

                                       JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, URL, null,
                                               new Response.Listener<JSONObject>() {
                                                   @Override
                                                   public void onResponse(JSONObject response) {
                                                       try{
                                                       JSONObject location = response.getJSONObject("field_geolocation");
                                                       JSONArray und = location.getJSONArray("und");

                                                       for (int j = 0; j < und.length(); j++){
                                                           String latitude = und.getJSONObject(j).getString("lat");
                                                           String longitude = und.getJSONObject(j).getString("lon");
                                                           mTextViewResult.append("Latitude: " + latitude + "\n");
                                                           mTextViewResult.append("Longitude: " + longitude + "\n");

                                                       }
                                                       JSONObject dateObject = response.getJSONObject("field_date");
                                                       JSONArray insideDate = dateObject.getJSONArray("und");
                                                       for (int k = 0; k < insideDate.length(); k++){
                                                           String longDate = insideDate.getJSONObject(k).getString("value");
                                                           String[] separated = longDate.split("T");
                                                           String date = separated[0];
                                                           String time = separated[1];
                                                           mTextViewResult.append("Date: " + date + "\n");
                                                           mTextViewResult.append("Time: " + time + "\n");
                                                       }
                                                       }catch (JSONException e){
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               }, new Response.ErrorListener() {
                                           @Override
                                           public void onErrorResponse(VolleyError error) {
                                               error.printStackTrace();
                                           }
                                       });
                                        mQueue.add(request1);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
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
