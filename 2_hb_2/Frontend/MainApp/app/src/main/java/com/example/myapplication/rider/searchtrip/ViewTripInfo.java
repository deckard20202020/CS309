package com.example.myapplication.rider.searchtrip;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.time.LocalDateTime;

public class ViewTripInfo extends AppCompatActivity {

    private static TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_trip_info);
        info = findViewById(R.id.InfoView);
        LocalDateTime startDate =(LocalDateTime) getIntent().getSerializableExtra("startDate");
        String startLocation = getIntent().getStringExtra("startLocation");
        String endLocation = getIntent().getStringExtra("endLocation");
        String yourStartLocation = getIntent().getStringExtra("yourStart");
        String yourEndLocation = getIntent().getStringExtra("yourEnd");

        info.setText( "Rider start time: " + prettyHoursAndMinutes(startDate.getHour(),
                startDate.getMinute()) + "\nRider start location: " + startLocation +
                "\nRider End Location: " + endLocation + "\nYour start Location" +
                yourStartLocation + "\nYour end location: " + yourEndLocation);
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


}
