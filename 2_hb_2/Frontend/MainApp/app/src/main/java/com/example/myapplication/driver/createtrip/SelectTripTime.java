package com.example.myapplication.driver.createtrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * allows drivers to choose the time of their trip
 */
public class SelectTripTime extends AppCompatActivity {
    private static String time = "";
    private static String date = "";
    /**
     * start date of the trip as a LocalDateTime object
     */
    public static LocalDateTime startDate;
    private static TextView timeTV;
    private static TextView dateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ride_time);
        dateTV = findViewById(R.id.dateSelectedTV);
        timeTV = findViewById(R.id.timeSelectedTV);
    }

    /**
     *  fragment that allows choosing of time in hour and minutes
     */
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        /**
         * @param view      the activity that is referencing this method
         * @param hour      start hour of trip
         * @param minute    start minute of trip
         */
        public void onTimeSet(TimePicker view, int hour, int minute) {
            String h = hour >= 10 ? String.valueOf(hour) : "0" + hour;
            String m = minute >= 10 ? String.valueOf(minute) : "0" + minute;
            SelectTripTime.time = h + ":" + m;
            SelectTripTime.timeTV.setText(prettyHoursAndMinutes(hour, minute));
        }
    }

    /**
     * fragment that allows choosing of date in terms of day, month, and year
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * @param view  the activity that is referencing this method
         * @param year  start year of trip
         * @param month start month of trip
         * @param day   start day of trip
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String m = (month + 1) >= 10 ? String.valueOf(month + 1) : "0" + (month + 1);
            String d = day >= 10 ? String.valueOf(day) : "0" + day;
            SelectTripTime.date = year + "-" + m + "-" + d;

            String dateTimeString = date + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            startDate = LocalDateTime.parse(dateTimeString, formatter);
            SelectTripTime.dateTV.setText(SelectTripTime.date + "(" + startDate.getDayOfWeek() + ")");
        }
    }

    private static String prettyHoursAndMinutes(int hour, int minute){
        if(hour > 12){
            if(minute >= 10) {
                return (hour - 12) + ":" + minute + " PM";
            }
            else{
                return (hour - 12) + ":0" + minute + " PM";
            }
        }
        else{
            if(minute >= 10) {
                return (hour) + ":" + minute + " PM";
            }
            else{
                return (hour) + ":0" + minute + " PM";
            }
        }
    }

    /**
     * Opens a fragment that allows for the start time of the trip to be chosen
     * @param v the activity that is referencing this method
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Opens a fragment that allows for the start date of the trip to be chosen
     * @param v the activity that is referencing this method
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Proceeds to the activity that allows for choosing the start and end location for a trip.
     * If editing, then we pass that boolean to the intent as well
     * @param v the activity that is referencing this method
     */
    public void selectStartLocation(View v){
        if(!date.equals("") && !time.equals("")) {
            String dateTimeString = date + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            startDate = LocalDateTime.parse(dateTimeString, formatter);
        }
        Intent i = new Intent(this, SelectTripPlace.class);
        try {
            if ((boolean) getIntent().getSerializableExtra("editing")) {
                i.putExtra("editing", true);
                i.putExtra("tripId", (int) getIntent().getSerializableExtra("tripId"));
            }
        } catch(Exception e){
            Log.e("error", e.toString());
        }
        startActivity(i);
    }
}