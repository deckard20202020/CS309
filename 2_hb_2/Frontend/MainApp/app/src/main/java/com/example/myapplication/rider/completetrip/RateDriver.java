package com.example.myapplication.rider.completetrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.app.AppController;
import com.example.myapplication.driver.completetrip.RateRidersAdapter;
import com.example.myapplication.driver.completetrip.TripCompleted;
import com.example.myapplication.endpoints.Endpoints;
import com.example.myapplication.rider.HomePage;
import com.example.myapplication.rider.TripsList;

import org.json.JSONObject;

/**
 * activity for rider rating/reviewing a driver
 */
public class RateDriver extends AppCompatActivity {

    private TextView tv;
    private EditText comments;
    private RatingBar rating;
    private int driverId;
    private int riderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_rate_driver);
        try {
            driverId = TripsList.tripDriverId;
            riderId = MainActivity.accountObj.getInt("id");

        }catch(Exception e){}
        tv = findViewById(R.id.ratingNameTV);
//        tv.setText("Rating: " + driverId);

        comments = findViewById(R.id.riderCommentingDriverET);
        rating = findViewById(R.id.riderRatingDriverBar);
    }

    /**
     * rider rates a driver from 0 - 5
     * @param view the view that is referencing this method
     */
    public void submitRating(View view){

        String rateUrl = Endpoints.CreateRatingUrl + riderId + "&ratedId=" + driverId;

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
     * rider leaves comments for a driver
     */
    public void submitReview() {

        String reviewUrl = Endpoints.PostDriverReview + driverId + "&riderId=" + riderId;

        JSONObject obj = new JSONObject();
        try {
            obj.put("review", comments.getText().toString());
        } catch (Exception e) { }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, reviewUrl, obj,
                response -> {
                    Intent i = new Intent(this, HomePage.class);
                    this.startActivity(i);
                },
                error -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show()));
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");

    }
}