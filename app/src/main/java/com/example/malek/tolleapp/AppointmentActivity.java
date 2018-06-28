package com.example.malek.tolleapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class AppointmentActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Button button;
    private TextView theDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        theDate = findViewById(R.id.textViewDate);
        // the incoming intent retrieves the date that is coming from CalendarActivity (with the key)
        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("keyDate") ;
        theDate.setText(date);

        //back to calendar button
        button = findViewById(R.id.backToCalendar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarActivity();
            }
        });

        //the Time picker
        button = findViewById(R.id.timePickerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

    }

    public void openCalendarActivity(){
        Intent intent = new Intent(this,CalendarActivity.class);
        startActivity(intent);
    }

    //The onTimeSet listener gets the hour and the minute and display them in the assigned Text view
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = findViewById(R.id.timeTextView);
        textView.setText(hourOfDay + " : "+minute);
    }

}
