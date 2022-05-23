package com.example.myapplication.driver.createtrip;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.*;
import com.example.myapplication.endpoints.Endpoints;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.OtherConstants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * allows drivers to choose origin and destination of their trip
 */
public class SelectTripPlace extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutocompleteSupportFragment autocompleteOriginFragment;
    private AutocompleteSupportFragment autocompleteDestFragment;
    static private LatLng origin;
    static private LatLng dest;
    /**
     * origin of the trip as a string
     */
    static public String originAddress;
    /**
     * destination of the trip as a string
     */
    static public String destAddress;

    /**
     * distance of the trip in kilometers
     */
    static public double distance;
    /**
     * duration of the trip's hours
     */
    static public int durationHours;
    /**
     * duration of the trips's minutes
     */
    static public int durationMinutes;

    static private Marker originMarker;
    static private Marker destMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        origin = null;
        dest = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ride_location);
        initAutoCompleteFragments();
    }

    private void initAutoCompleteFragments(){
        Places.initialize(getApplicationContext(), OtherConstants.GoogleMapsAPIKey);

        autocompleteOriginFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_origin_fragment);
        autocompleteOriginFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteOriginFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("Maps", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                origin = place.getLatLng();
                originAddress = place.getAddress();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 12.0f));


                if(originMarker != null)
                    originMarker.remove();
                originMarker = mMap.addMarker(new MarkerOptions().position(origin));

                if (origin != null && dest != null) {
                    clearMapAndDraw();
                }
            }
            @Override
            public void onError(@NonNull Status status) { Log.e("Maps error", status.toString()); }
        });

        autocompleteDestFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_dest_fragment);
        autocompleteDestFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteDestFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("Maps", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                dest = place.getLatLng();
                destAddress = place.getAddress();

                if(destMarker != null)
                    destMarker.remove();
                destMarker = mMap.addMarker(new MarkerOptions().position(dest));

                if (origin != null && dest != null){
                    clearMapAndDraw();
                }

            }
            @Override
            public void onError(@NonNull Status status) { Log.e("Maps error", status.toString()); }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Initializes google maps object
     * @param googleMap Google Maps object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    private void drawRoute(){
        String routeOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String waypoints = "";
        String routeDest = "destination=" + dest.latitude + "," + dest.longitude;
        String params = routeOrigin + "&" + waypoints + "&"  + routeDest + "&key=" + OtherConstants.GoogleMapsAPIKey;
        String url = Endpoints.GoogleMapsDirectionUrl + params;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
            response -> {
                try {
                    JSONArray routeArray = response.getJSONArray("routes");
                    JSONObject routes = routeArray.getJSONObject(0);
                    JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                    String encodedString = overviewPolylines.getString("points");
                    List<LatLng> list = PolyUtil.decode(encodedString);
                    mMap.addPolyline(new PolylineOptions().addAll(list).width(12).color(Color.parseColor("#05b1fb")).geodesic(true));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 10.0f));
                    calculateRoute();
                }
                catch(JSONException e){ Log.e("Maps error", e.toString()); }
            },
            error -> Log.e("Maps error", error.toString())
        );
        AppController.getInstance().addToRequestQueue(req, "obj_req");
    }

    private void calculateRoute(){
        String routeOrigin = "origins=" + origin.latitude + "," + origin.longitude;
        String routeDest = "destinations=" + dest.latitude + "," + dest.longitude;
        String params = routeOrigin + "&"  + routeDest + "&units=imperial" + "&key=" + OtherConstants.GoogleMapsAPIKey;
        params = params.replaceAll(" ", "%20");
        String url = Endpoints.GoogleMapsDistanceUrl + params;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
            response -> {
                try {
                    JSONObject rows0 = response.getJSONArray("rows").getJSONObject(0);
                    JSONArray elements = (JSONArray) rows0.get("elements");
                    distance = elements.getJSONObject(0).getJSONObject("distance").getInt("value") / 1000.0; //in meters, so divide to get in km
                    int durationSeconds = elements.getJSONObject(0).getJSONObject("duration").getInt("value");
                    durationHours = durationSeconds / 3600;
                    durationMinutes = (durationSeconds % 3600) / 60;
                }
                catch(JSONException e){ Log.e("Maps error", e.toString()); }
            },
            error -> Log.e("Maps error", error.toString()));
        AppController.getInstance().addToRequestQueue(req, "obj_req");
    }

    /**
     * Proceeds to the activity that shows final trip details and allows for selecting radius, max riders, and rate per minute
     * If editing, then we pass that boolean to the intent as well
     * @param v the activity that is referencing this method
     */
    public void proceed(View v){
        Intent i = new Intent(this, ConfirmTrip.class);
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

    private void clearMapAndDraw(){
        mMap.clear();
        originMarker = mMap.addMarker(new MarkerOptions().position(origin));
        destMarker = mMap.addMarker(new MarkerOptions().position(dest));
        drawRoute();
    }

}
