package com.example.myapplication.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.Endpoints;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * rider can view their rating and reviews
 */
public class Reviews extends AppCompatActivity {

    private TextView ratingTV;
    private TextView reviewsTV;
    private ArrayList<String> reviews;
    private int riderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_reviews);

        try{
            riderId = MainActivity.accountObj.getInt("id");
        } catch(Exception e){}

        ratingTV = findViewById(R.id.riderRatingTV);
        reviewsTV = findViewById(R.id.riderReviewsTV);
        reviews = new ArrayList<>();

        getRating();
        getReviews();
    }

    private void getRating() {
        String url = Endpoints.GetUserRating + riderId;
        StringRequest req = new StringRequest(Request.Method.GET, url,
            response -> ratingTV.setText("Rating: " + response),
            error -> Log.e("error", error.toString())
        );
        AppController.getInstance().addToRequestQueue(req, "get_string_req");
    }

    private void getReviews() {
        String url = Endpoints.GetRiderReviews + riderId;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            reviews.add(obj.getString("review"));
                        }
                    }catch(Exception e){}
                    prettyPrintReviews();
                },
                error -> Log.e("error", error.toString())
        );
        AppController.getInstance().addToRequestQueue(req, "get_array_req");
    }

    private void prettyPrintReviews(){
        String setme = "Reviews: \n\n";
        for(String review : reviews){
            setme += "\t\t- " + review + "\n\n";
        }
        reviewsTV.setText(setme);
    }
}