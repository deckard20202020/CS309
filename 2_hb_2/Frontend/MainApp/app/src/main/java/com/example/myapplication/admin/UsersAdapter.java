package com.example.myapplication.admin;

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
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileSettings;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.myapplication.endpoints.Endpoints;

/**
 * adapter class that shows all non-admin users
 */
public class UsersAdapter extends BaseAdapter implements ListAdapter {

    private JSONArray list;
    private Context context;
    private Button editUserButton;
    private Button deleteUserButton;
    private TextView tv;

    /**
     * creates an UsersAdapter object
     * @param list list of users
     * @param context context to put the list on
     */
    public UsersAdapter(JSONArray list, Context context) {
        for(int i = 0; i < list.length(); i++){
            try {
                JSONObject user = list.getJSONObject(i);
                try {
                    if (user.getBoolean("anAdmin") == true) {
                        list.remove(i);
                    }
                } catch(Exception e1) {}
            } catch(Exception e2) {}
        }
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
            view = inflater.inflate(R.layout.admin_user_item, null);
            editUserButton = view.findViewById(R.id.editUserButton);
            deleteUserButton = view.findViewById(R.id.deleteUserButton);
            tv = view.findViewById(R.id.chatWithRiderTV);
        }

        try {
            JSONObject json = list.getJSONObject(position);
            Log.d("json", json.toString());
            tv.setText("ID: " + json.getString("id") + ": " + json.getString("firstName") + " " + json.getString("lastName"));
        }
        catch(Exception e){
            Log.e("error", e.toString());
        }

        editUserButton.setOnClickListener(v -> editUser(position));
        deleteUserButton.setOnClickListener(v -> deleteUser(position));

        return view;
    }

    /**
     * edits a user
     * @param position position of the trip in the list
     */
    public void editUser(int position){
        try {
            MainActivity.accountObj = list.getJSONObject(position);
            Intent i = new Intent(this.context, ProfileSettings.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(i);
        } catch(Exception e) {
            Log.e("error", e.toString());
        }
    }

    /**
     * deletes a user
     * @param position position of the user in the list
     */
    public void deleteUser(int position){
        try {
            JSONObject json = list.getJSONObject(position);
            int id = json.getInt("id");
            String url = Endpoints.DeleteUserUrl + id;
            StringRequest req = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    Toast toast = Toast.makeText(this.context, "Successfully deleted user", Toast.LENGTH_LONG);
                    toast.show();
                    Intent i = new Intent(this.context, UsersList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.context.startActivity(i);
                },
                error -> {
                    Log.e("error", error.toString());
                    Toast toast = Toast.makeText(this.context, "Error deleting user", Toast.LENGTH_LONG);
                    toast.show();
                }
            );
            AppController.getInstance().addToRequestQueue(req, "post_object_tag");
        }
        catch(Exception e){
            Log.e("error", e.toString());
        }
    }
}
