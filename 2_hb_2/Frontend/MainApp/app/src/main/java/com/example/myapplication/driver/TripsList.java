package com.example.myapplication.driver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * shows all of the trips created by the driver, allowing for editing and deleting of them
 */
public class TripsList extends AppCompatActivity {

    private ListView listView;
    private JSONArray tripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trips_list);
        listAllTrips();
    }

    private void listAllTrips(){
        listView = findViewById(R.id.driverTripsListView);
        String url = Endpoints.AllDriverTripsUrl + MainActivity.accountId;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
            response -> {
                Log.e("response", response.toString());
                if(response != null) {
                    tripsList = response;
                    listView.setAdapter(new TripsAdapter(tripsList, getApplicationContext()));
                }
            },
            error -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG));
        AppController.getInstance().addToRequestQueue(req, "array_req");
    }

    /**
     * when back button is pressed, the activity becomes the driver's home page
     */
    public void onBackPressed() {
        Intent i = new Intent(this, HomePage.class);
        this.startActivity(i);
        super.onBackPressed();
    }

}