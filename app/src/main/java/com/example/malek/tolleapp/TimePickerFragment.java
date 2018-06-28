package com.example.malek.tolleapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    //This dialog is to open the dialog to choose the time of the meeting
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // To get the current hour and minute
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //the parameters of TimePickerDialog are: Context (our current activity); onTimeSetListener (where we send the time we choose);
        //current hour and current minute (depending on the format of the user either 24 or 12)
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
