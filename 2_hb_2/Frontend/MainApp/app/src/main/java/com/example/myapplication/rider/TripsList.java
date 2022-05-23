package com.example.myapplication.rider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.Endpoints;

import org.json.JSONArray;
import org.json.JSONObject;

public class TripsList extends AppCompatActivity {

    private ListView listView;
    private JSONArray arr;
    public static JSONObject trip;
    public static int tripId;
    public static int tripDriverId;
    public static String tripDriverEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_trip_list);
        listView = findViewById(R.id.riderTripsListView);

        setList();
    }

    /**
     * sets list of trips of rider
     */
    private void setList() {
        try {
        String url = Endpoints.GetRiderTrips + MainActivity.accountObj.getInt("id");
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.e("response", response.toString());
                        if(response != null) {
                            arr = response;
                            listView.setAdapter(new TripsAdapter(arr, "TripsList", getApplicationContext()));
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG));
            AppController.getInstance().addToRequestQueue(req, "array_req");
        }
        catch (Exception e){}
    }

}
