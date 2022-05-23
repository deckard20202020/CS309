package com.example.myapplication.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.Endpoints;
import com.example.myapplication.endpoints.OtherConstants;
import com.google.android.gms.location.FusedLocationProviderClient;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.myapplication.*;

import org.json.JSONObject;

import java.util.Locale;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class OngoingTrip extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    private CameraPosition cameraPosition;
    private PlacesClient placesClient;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final float DEFAULT_ZOOM = 18.0f;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private Location lastKnownLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private WebSocketClient cc;

    private String myOriginString;
    private Location myOriginLocation;
    private String myDestinationString;
    private Location myDestinationLocation;
    private Geocoder geocoder;
    private boolean trackDriverBoolean;

    private Location driverLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_driver_ongoing_trip);

        myOriginLocation = new Location("");
        myDestinationLocation = new Location("");
        driverLocation = new Location("");
        geocoder = new Geocoder(this, Locale.getDefault());
        trackDriverBoolean = true;

        getMyStops();
        connect();

        Places.initialize(getApplicationContext(), OtherConstants.GoogleMapsAPIKey);
        placesClient = Places.createClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    public void onMapReady(GoogleMap map) {
        this.map = map;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.e("error", "Exception: %s", task.getException());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void connect(){

        Draft[] drafts = {new Draft_6455()};
        String url = "ws://coms-309-030.class.las.iastate.edu:8080/location/%7B" + MainActivity.accountEmail + "%7D" + "/%7B" + TripsList.tripId + "%7D";
        Log.e("error", "" + TripsList.tripId);
        Log.e("error", url);
        try {
            Log.e("error", "Trying socket");
            cc = new WebSocketClient(new URI(url), drafts[0]) {
                @Override
                public void onMessage(String message) {
                    if(trackDriverBoolean) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            try {
                                String latlng[] = message.split(":");
                                double latitude = Double.parseDouble(latlng[0]);
                                double longititude = Double.parseDouble(latlng[1]);
                                map.clear();

                                Log.e("error", "driver location update");
                                map.addMarker(new MarkerOptions().position(new LatLng(latitude, longititude)).flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longititude), DEFAULT_ZOOM));

                                Address driverAddress = geocoder.getFromLocation(latitude, longititude, 1).get(0);
                                driverLocation.setLatitude(driverAddress.getLatitude());
                                driverLocation.setLongitude(driverAddress.getLongitude());
                                Log.e("error", "Driver distance: " + driverLocation.distanceTo(myOriginLocation));

                                // Driver has picked me up; now we are the same location
                                if (driverLocation.distanceTo(myOriginLocation) < 300) {
                                    trackDriverBoolean = false;
                                    map.clear();
                                }
                            } catch (Exception e) { }
                        });
                    }
                }
                @Override
                public void onOpen(ServerHandshake handshake) { }
                @Override
                public void onClose(int code, String reason, boolean remote) { }
                @Override
                public void onError(Exception e) { Log.e("error:", e.toString()); }
            };
        } catch (URISyntaxException e) { Log.e("error:", e.getMessage()); }
        cc.connect();
    }

    private void getMyStops(){
        try{
            int tripId = TripsList.tripId;
            String url = Endpoints.GetRiderStops + tripId;
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject obj = response.getJSONObject(i);
                                if(obj.getInt("riderId") == MainActivity.accountId) {
                                    myOriginString = obj.getString("riderOriginAddress");
                                    myDestinationString = obj.getString("riderDestAddress");
                                    try {
                                        Address myOriginAddress = geocoder.getFromLocationName(myOriginString, 1).get(0);
                                        myOriginLocation.setLatitude(myOriginAddress.getLatitude());
                                        myOriginLocation.setLongitude(myOriginAddress.getLongitude());

                                        Address myDestinationAddress = geocoder.getFromLocationName(myDestinationString, 1).get(0);
                                        myDestinationLocation.setLatitude(myDestinationAddress.getLatitude());
                                        myDestinationLocation.setLongitude(myDestinationAddress.getLongitude());

                                        locationListener = location -> {
                                            Log.e("error", location.toString() + "Distance to my destination: " + location.distanceTo(myDestinationLocation));

                                            // We have arrived
                                            if(location.distanceTo(myDestinationLocation) < 300){
                                                Log.e("error", "arrived at location");
                                                Intent intent = new Intent(this, com.example.myapplication.rider.completetrip.RateDriver.class);
                                                this.startActivity(intent);
                                                super.onBackPressed();
                                            }
                                            // Update camera if I move
                                            else {
                                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
                                                Log.e("error", "location change: " + location.getLatitude() + " " + location.getLongitude() + " | dist : " + myDestinationLocation.toString());
                                            }
                                        };
                                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return; }
                                        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
                                    }
                                    catch(Exception e){
                                        Log.e("error", e.toString());
                                    }
                                }
                            }
                        }
                        catch(Exception e){
                            Log.e("error", e.toString());
                        }
                    },
                    error -> Log.e("error", error.toString())
            );
            AppController.getInstance().addToRequestQueue(req, "json_array_req");


        }catch(Exception e){}
    }
}