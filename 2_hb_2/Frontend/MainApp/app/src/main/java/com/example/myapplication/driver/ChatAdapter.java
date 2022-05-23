package com.example.myapplication.driver;

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

import com.example.myapplication.Chat;
import com.example.myapplication.R;

import org.json.JSONArray;

/**
 * adapter class that shows all the trips ever made by the driver
 */
public class ChatAdapter extends BaseAdapter implements ListAdapter {
    private JSONArray list;
    private Context context;
    private Button chatButton;
    private TextView tv;

    /**
     * creates a ChatAdapter object
     * @param list list of users to chat with
     * @param context context to put the list on
     */
    public ChatAdapter(JSONArray list, Context context) {
        this.list = list;
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

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.driver_rider_chat_item, null);
            chatButton = view.findViewById(R.id.chatWithRiderButton);
            tv = view.findViewById(R.id.chatWithRiderTV);
        }

        try {
            String name = list.getString(position);
            tv.setText(name);
        }
        catch(Exception e){
            Log.e("error", e.toString());
        }

        chatButton.setOnClickListener(v -> chat(position));

        return view;
    }

    /**
     * edits a trip
     * @param position position of the trip in the list
     */
    public void chat(int position){
        Log.e("error", "trying to chat");
        try {
            String riderName = list.getString(position);
            String receiverEmail = TripDetail.nameToEmailMap.get(riderName);
            int receiverId = TripDetail.nameToIdMap.get(riderName);
            Log.e("error", riderName + " " + receiverEmail);
            Intent intent = new Intent(this.context, Chat.class);
            intent.putExtra("receiverEmail", receiverEmail);
            intent.putExtra("receiverId", receiverId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        }catch(Exception e){
            Log.e("error", e.toString());
        }

    }
}
