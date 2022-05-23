package com.example.myapplication.rider.searchtrip;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.OtherConstants;
import com.example.myapplication.rider.TripsAdapter;
import com.example.myapplication.endpoints.Endpoints;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.slider.Slider;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * where riders can search for a trip
 */
public class SearchPage extends AppCompatActivity {

    private ListView listView;
    private JSONArray tripsList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public static String originAddress;
    public static String destAddress;
    private int durationHours;
    private int durationMinutes;
    private LatLng driverOrigin;
    private LatLng driverDest;
    private double newDistance;
    private double driverDistance;
    private RequestFuture<String> future;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_search_page);

        if(SearchTripTime.startDate != null){
            startDate = SearchTripTime.startDate;
        }
        if(startDate != null && (SearchTripPlace.durationHours != 0 || SearchTripPlace.durationMinutes != 0)){
            durationHours = SearchTripPlace.durationHours;
            durationMinutes = SearchTripPlace.durationMinutes;
            endDate = SearchTripTime.startDate.plusHours(durationHours).plusMinutes(durationMinutes);
        }
        if(SearchTripPlace.originAddress != null){
            originAddress = SearchTripPlace.originAddress;
        }
        if(SearchTripPlace.destAddress != null){
            destAddress = SearchTripPlace.destAddress;
        }
    }

    /**
     * With fields filled out, searches all available trips implementing
     * details provided
     * @param view the activity that is referencing this method
     */
    public void searchTrips(View view)
    {
        listView = findViewById(R.id.riderTripsListView);
        String url = Endpoints.RiderSearchTripUrl + startDate +
                "&scheduledEndDate=" + endDate;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.e("response", response.toString());
                    if(response != null) {
                        tripsList = response;
                        sortList();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG));
        AppController.getInstance().addToRequestQueue(req, "array_req");
    }

    private void sortList()
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        JSONArray filteredList = new JSONArray();
        future = RequestFuture.newFuture();
        for(int i = 0; i < tripsList.length(); i++)
        {
            try {
            JSONObject temp = tripsList.getJSONObject(i);
            List<Address>startAddressList = geocoder.getFromLocationName(temp.getString("originAddress"),1);
            List<Address>endAddressList = geocoder.getFromLocationName(temp.getString("destAddress"),1);
                if(startAddressList.size() > 0 && endAddressList.size() > 0)
                {
                    driverOrigin = new LatLng(startAddressList.get(0).getLatitude(), startAddressList.get(0).getLongitude());
                    driverDest = new LatLng(endAddressList.get(0).getLatitude(), endAddressList.get(0).getLongitude());
                    Log.e("Lat/long", driverOrigin.toString());
                    String routeOrigin = "origins=" + driverOrigin.latitude + "," + driverOrigin.longitude
                            + "|" + SearchTripPlace.origin.latitude + "," + SearchTripPlace.origin.longitude
                            + "|" + SearchTripPlace.dest.latitude + "," + SearchTripPlace.dest.longitude;
                    Log.e("route start", routeOrigin);
                    String routeDest = "destinations=" + SearchTripPlace.origin.latitude + "," + SearchTripPlace.origin.longitude
                            + "|" + SearchTripPlace.dest.latitude + "," + SearchTripPlace.dest.longitude
                            + "|" + driverDest.latitude + "," + driverDest.longitude;
                    Log.e("route dest", routeDest);
                    String params = routeOrigin + "&"  + routeDest + "&units=imperial" + "&key=" + OtherConstants.GoogleMapsAPIKey;
                    params = params.replaceAll(" ", "%20");
                    String url = Endpoints.GoogleMapsDistanceUrl + params;
                    makeRequest(url, temp, filteredList);
                }
            }
            catch (Exception e){}
        }

    }

    private void makeRequest(String url, JSONObject item, JSONArray list)
    {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> {
                    try {
                        JSONObject rows0 = response.getJSONArray("rows").getJSONObject(0);
                        JSONObject rows1 = response.getJSONArray("rows").getJSONObject(1);
                        JSONObject rows2 = response.getJSONArray("rows").getJSONObject(2);
                        JSONArray elements1 = (JSONArray) rows0.get("elements");
                        JSONArray elements2 = (JSONArray) rows1.get("elements");
                        JSONArray elements3 = (JSONArray) rows2.get("elements");
                        double dist1 = elements1.getJSONObject(0).getJSONObject("distance").getInt("value") / 1000.0; //in meters, so divide to get in km
                        Log.e("Distance 1", "" + dist1);
                        double dist2 = elements2.getJSONObject(1).getJSONObject("distance").getInt("value") / 1000.0; //in meters, so divide to get in km
                        Log.e("Distance 2", "" + dist2);
                        double dist3 = elements3.getJSONObject(2).getJSONObject("distance").getInt("value") / 1000.0; //in meters, so divide to get in km
                        Log.e("Distance 3", "" + dist3);
                        driverDistance = elements1.getJSONObject(2).getJSONObject("distance").getInt("value") / 1000.0; //in meters, so divide to get in km
                        Log.e("Driver distance", "" + driverDistance);
                        newDistance = dist1 + dist2 + dist3;
                        Log.e("Distance", "" + newDistance);
                        if(newDistance != -1 && (newDistance - driverDistance < (item.getDouble("radius")) * 1.6))
                        {
                            list.put(item);
                            listView.setAdapter(new TripsAdapter(list, "SearchPage", getApplicationContext()));
                        }
                    }
                    catch(JSONException e){ Log.e("Maps error", e.toString()); newDistance = -1; }
                },
                error -> {Log.e("Maps error", error.toString()); newDistance = -1;});
        AppController.getInstance().addToRequestQueue(req, "obj_req");

    }
}
