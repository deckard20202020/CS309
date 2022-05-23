package com.example.myapplication.driver.completetrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.myapplication.*;
import com.example.myapplication.driver.HomePage;
import com.example.myapplication.driver.TripDetail;

/**
 * activity that appears when a trip is completed, allowing driver to choose from riders to rate
 */
public class TripCompleted extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trip_completed);
        listAllRiders();
    }

    private void listAllRiders(){
        try {
            listView = findViewById(R.id.driverTripsListView);
            listView.setAdapter(new RateRidersAdapter(TripDetail.riderNames, getApplicationContext()));
        } catch(Exception e){}
    }

    /**
     * finishes a trip and goes back to home screen
     * @param v view that is referencing this method
     */
    public void finishTrip(View v){
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }
}