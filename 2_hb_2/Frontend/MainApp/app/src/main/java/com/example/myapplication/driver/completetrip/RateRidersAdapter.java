package com.example.myapplication.driver.completetrip;

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

import com.example.myapplication.R;

import org.json.JSONArray;

/**
 * adapter class that shows all the riders that a driver can rate
 */
public class RateRidersAdapter extends BaseAdapter implements ListAdapter {

    private static JSONArray list;
    private Context context;

    /**
     * current position in the list of riders
     */
    public static int currentPosition;
    /**
     * current name of the rider in the list
     */
    public static String currentRiderString;

    /**
     * creates a RateRidersAdapter object
     * @param list list of trips
     * @param context context to put the list on
     */
    public RateRidersAdapter(JSONArray list, Context context) {
        try {
            Log.e("error", list.getString(0));
        }catch(Exception e){}

        if(list == null){
            this.list = null;
        }
        else {
            this.list = list;
        }
        this.context = context;
    }

    /**
     * get size of list
     * @return size of list
     */
    @Override
    public int getCount() {
        return list.length();
    }

    /**
     * gets Object in list at specificed position
     * @param pos position of object
     * @return object as position pos
     */
    @Override
    public Object getItem(int pos) {
        try {
            return list.get(pos);
        }
        catch(Exception e){ return new Object(); }
    }

    /**
     * returns 0
     * @param pos unused variable
     * @return 0
     */
    @Override
    public long getItemId(int pos) {
        return 0;
    }

    /**
     * describes how the list elements are displayed
     * @param position position in list
     * @param convertView convertView object
     * @param parent parent for this view
     * @return the created View object
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(list != null) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.driver_rate_rider_item, null);
            }

            TextView tv = view.findViewById(R.id.chatWithRiderTV);
            try {
                tv.setText(list.getString(position));
            } catch (Exception e) { }

            Button rateRiderButton = view.findViewById(R.id.driverRateRiderButton);
            rateRiderButton.setOnClickListener(v -> rateRider(position));

            return view;
        }
        return null;
    }

    /**
     * rates a rider by proceeding to the next activity
     * @param position position of the rider in the list
     */
    public void rateRider(int position){
        try {
            currentRiderString = list.getString(position);
            currentPosition = position;
            Intent i = new Intent(this.context, RateRider.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(i);
        }
        catch(Exception e){}
    }

    /**
     * once a rating/review is submitted successfully, the rider cannot be rated/reviewed anymore -> remove them from list
     * @param position
     */
    public static void finishRating(int position){
        list.remove(position);
    }
}
