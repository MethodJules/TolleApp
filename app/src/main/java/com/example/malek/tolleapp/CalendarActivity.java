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

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private Button button;
    public CalendarView mCalendarView;


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

        button = findViewById(R.id.appointmentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAppointmentActivity();
            }
        });

        button = findViewById(R.id.backToMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
    }

    //this method is set to the button @zurück to go back to the home Page of InDeko
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //this method is set to the button @Termin to go to the appointment activity
    public void openAppointmentActivity(){
        Intent intent = new Intent(this, AppointmentActivity.class);
        startActivity(intent);
    }



}
