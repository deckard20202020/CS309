package com.example.myapplication.driver.completetrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;
import com.example.myapplication.driver.TripDetail;
import com.example.myapplication.endpoints.Endpoints;

import org.json.JSONObject;

/**
 * activity for driver rating/reviewing a rider
 */
public class RateRider extends AppCompatActivity {

    private TextView tv;
    private EditText comments;
    private RatingBar rating;
    private int driverId;
    private int riderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rate_rider);
        try {
            driverId = MainActivity.accountObj.getInt("id");
            riderId = TripDetail.nameToIdMap.get(RateRidersAdapter.currentRiderString);

        }catch(Exception e){}
        tv = findViewById(R.id.ratingNameTV);
        tv.setText("Rating: " + RateRidersAdapter.currentRiderString);

        comments = findViewById(R.id.driverCommentingRiderET);
        rating = findViewById(R.id.driverRatingRiderBar);
    }

    /**
     * driver rates a rider from 0 - 5
     * @param view the view that is referencing this method
     */
    public void submitRating(View view){

        String rateUrl = Endpoints.CreateRatingUrl + driverId + "&ratedId=" + riderId;

        JSONObject obj = new JSONObject();
        try {
            obj.put("rating", rating.getRating());
        } catch(Exception e){ }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, rateUrl, obj,
                response -> submitReview(),
                error -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show()));
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");
    }

    /**
     * driver leaves comments for a rider
     */
    public void submitReview() {

        String reviewUrl = Endpoints.PostRiderReview + driverId + "&riderId=" + riderId;

        JSONObject obj = new JSONObject();
        try {
            obj.put("review", comments.getText().toString());
        } catch (Exception e) { }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, reviewUrl, obj,
                response -> {
                    RateRidersAdapter.finishRating(RateRidersAdapter.currentPosition);
                    Intent intent = new Intent(this, TripCompleted.class);
                    startActivity(intent);
                },
                error -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show()));
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");
    }
}