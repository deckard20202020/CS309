package com.example.myapplication.rider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.Endpoints;
import com.example.myapplication.rider.searchtrip.SearchTripPlace;
import com.example.myapplication.rider.searchtrip.ViewTripInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

/**
 * adapter class that shows all the trips of a rider
 */
public class TripsAdapter extends BaseAdapter implements ListAdapter {
    private JSONArray list;
    private Context context;
    private String page;

    /**
     * creates a TripsAdapter object
     *
     * @param list    list of trips
     * @param context context to put the list on
     */
    public TripsAdapter(JSONArray list, String page, Context context) {
        if (list == null) {
            this.list = null;
        } else {
            this.list = list;
        }
        this.context = context;
        this.page = page;
    }

    /**
     * get size of list
     *
     * @return size of list
     */
    @Override
    public int getCount() {
        return list.length();
    }

    /**
     * gets Object in list at specificed position
     *
     * @param pos position of object
     * @return object as position pos
     */
    @Override
    public Object getItem(int pos) {
        try {
            return list.get(pos);
        } catch (Exception e) {
            return new Object();
        }
    }

    /**
     * returns 0
     *
     * @param pos unused variable
     * @return 0
     */
    @Override
    public long getItemId(int pos) {
        return 0;
    }

    /**
     * describes how the list elements are displayed
     *
     * @param position    position in list
     * @param convertView convertView object
     * @param parent      parent for this view
     * @return the created View object
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (list != null && page.equals("SearchPage")) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.rider_trip_item, null);
            }

            TextView tv = view.findViewById(R.id.riderListText);
            try {
                JSONObject json = list.getJSONObject(position);
                Log.e("Json logging", json.toString());
                tv.setText("From: " + json.getString("originAddress") + "\nTo: " + json.getString("destAddress") +
                        "\nTime: " + json.getString("scheduledStartDate") + "\n->" + json.getString("scheduledEndDate"));
            } catch (Exception e) {
            }

            Button addToTripButton = view.findViewById(R.id.add_to_trip);
            Button viewTripButton = view.findViewById(R.id.view_trip);
            addToTripButton.setOnClickListener(v -> {
                addToTrip(position);
            });
            viewTripButton.setOnClickListener(v -> {
                viewCandidateTrip(position);
            });
            return view;
        }

        else if(list != null && page.equals("TripsList")) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.rider_trip_info_item, parent, false);
            }

            TextView tv = view.findViewById(R.id.riderListText);
            try {
                JSONObject json = list.getJSONObject(position);
                Log.e("Json logging", json.toString());
                tv.setText("From: " + json.getString("originAddress") + "\nTo: " + json.getString("destAddress") +
                        "\nTime: " + json.getString("scheduledStartDate") + "\n->" + json.getString("scheduledEndDate"));
            } catch (Exception e) { }

            Button removeFromTripButton = view.findViewById(R.id.delete_trip);
            Button viewTripButton = view.findViewById(R.id.riderViewTripButton);
            Button chatWithDriverButton = view.findViewById(R.id.chatWithDriverButton);

            removeFromTripButton.setOnClickListener(v -> removeFromTrip(position));
            chatWithDriverButton.setOnClickListener(v -> chat(position));
            viewTripButton.setOnClickListener(v -> viewMyTrip(position));

            return view;
        }
        return null;

    }

    /**
     * Adds a rider to a trip based off requirements radius and location requirements
     * @param position
     */
    public void addToTrip(int position) {
       try{
           String url = Endpoints.AddRiderToTripUrl+list.getJSONObject(position).getInt("id")+
                   "&riderId="+MainActivity.accountObj.getInt("id") + "&riderOriginAddress=" +
                   SearchTripPlace.originAddress + "&riderDestAddress=" + SearchTripPlace.destAddress;
           StringRequest req = new StringRequest(Request.Method.PUT, url,
                   response -> {
                       Intent i = new Intent(this.context, HomePage.class);
                       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       this.context.startActivity(i);
                       Toast toast = Toast.makeText(this.context, "Successfully added to trip", Toast.LENGTH_LONG);
                       toast.show();
                   },
                   error -> {
                       Log.e("trips error", error.toString());
                       Toast toast = Toast.makeText(this.context, "Error adding trip", Toast.LENGTH_LONG);
                       toast.show();
                   }
           );
           AppController.getInstance().addToRequestQueue(req, "string_req");
       }
       catch (Exception e){}
    }

    public void viewCandidateTrip(int position){
        try {
            String startDate = list.getJSONObject(position).getString("scheduledStartDate");
            String startLocation = list.getJSONObject(position).getString("originAddress");
            String endLocation = list.getJSONObject(position).getString("destAddress");
            String yourStart = com.example.myapplication.rider.searchtrip.SearchTripPlace.originAddress;
            String yourEnd = com.example.myapplication.rider.searchtrip.SearchTripPlace.destAddress;
            LocalDateTime startD = LocalDateTime.parse(startDate);
            Intent i = new Intent(this.context, ViewTripInfo.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("startDate", startD);
            i.putExtra("startLocation", startLocation);
            i.putExtra("endLocation", endLocation);
            i.putExtra("yourStart", yourStart);
            i.putExtra("yourEnd", yourEnd);
           // i.putExtra("prevPage", );
            this.context.startActivity(i);
        }catch (Exception e){Log.e("Error popup", "" + e);}
    }

    public void removeFromTrip(int position)
    {
        try {
            String url = Endpoints.RemoveRiderFromTrip + list.getJSONObject(position).getInt("id")
                    +"&riderId=" + MainActivity.accountObj.getInt("id");
            StringRequest req = new StringRequest(Request.Method.PUT, url,
                    response -> {
                        Intent i = new Intent(this.context, TripsList.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.context.startActivity(i);
                        Toast toast = Toast.makeText(this.context, "Successfully removed from trip", Toast.LENGTH_LONG);
                        toast.show();
                    },
                    error -> {
                        Log.e("trips error", error.toString());
                        Toast toast = Toast.makeText(this.context, "Error adding trip", Toast.LENGTH_LONG);
                        toast.show();
                    }
            );
            AppController.getInstance().addToRequestQueue(req, "string_req");

        }
        catch (Exception e) {}
    }

    public void chat(int position){
        try {
            JSONObject obj = list.getJSONObject(position);
            String url = Endpoints.GetTripDriverUrl + obj.getInt("id");
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            String receiverEmail = response.getString("email");
                            int receiverId = response.getInt("id");
                            Log.e("error", receiverEmail);
                            Log.e("error", receiverId + "");
                            Intent i = new Intent(this.context, Chat.class);
                            i.putExtra("receiverEmail", receiverEmail);
                            i.putExtra("receiverId", receiverId);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            this.context.startActivity(i);
                        }
                        catch(Exception e){
                            Log.e("error", e.toString());
                        }
                    },
                    error -> {
                        Log.e("error", error.toString());
                    }
            );
            AppController.getInstance().addToRequestQueue(req, "get_obj_req");
        }
        catch(Exception e) {
            Log.e("error", e.toString());
        }
    }

    public void viewMyTrip(int position) {
        try {
            JSONObject obj = list.getJSONObject(position);
            if (obj.getBoolean("hasStarted")) {
                String url = Endpoints.GetTripDriverUrl + obj.getInt("id");
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            TripsList.trip = obj;
                            TripsList.tripDriverId = response.getInt("id");
                            TripsList.tripId = TripsList.trip.getInt("id");
                            TripsList.tripDriverEmail = response.getString("email");
                            Intent intent = new Intent(this.context, OngoingTrip.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            this.context.startActivity(intent);
                        } catch (Exception e) {
                            Log.e("error", e.toString());
                        }
                    },
                    error -> Log.e("error", error.toString())
                );
                AppController.getInstance().addToRequestQueue(req, "get_obj_req");
            }
            else{
                Toast toast = Toast.makeText(this.context, "Trip has not started", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        catch(Exception e) {
            Log.e("error", e.toString());
        }
    }
}
