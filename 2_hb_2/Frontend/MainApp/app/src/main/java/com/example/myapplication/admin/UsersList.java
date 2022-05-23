package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.*;
import com.example.myapplication.app.AppController;

import org.json.JSONArray;

/**
 * page that shows all the users using the UsersAdapter class
 */
public class UsersList extends AppCompatActivity {

    private ListView listView;
    private JSONArray usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        listView = findViewById(R.id.driverTripsListView);
        String url = "";
        url = "http://coms-309-030.class.las.iastate.edu:8080/user/getAllUsers";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
            response -> {
                Log.e("Users list error", response.toString());
                usersList = response;
                listView.setAdapter(new UsersAdapter(usersList, getApplicationContext()) );
            },
            error -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG));
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");
    }

    /**
     * goes back to the admin home page
     */
    public void onBackPressed() {
        Intent i = new Intent(this, HomePage.class);
        this.startActivity(i);
        super.onBackPressed();
    }
}