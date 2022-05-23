package com.example.volleyremoteserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.volleyremoteserver.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private String tag_string_req = "string_req";
    private String tag_object_req = "object_req";
    private String tag_array_req = "array_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerRider(View view) throws JSONException {
        TextView textView = (TextView) findViewById(R.id.textview);
        String url = "http://coms-309-030.class.las.iastate.edu:8080/rider/registerRider/";
        JSONObject obj = new JSONObject();
        obj.put("firstName","Yaaseen");
        obj.put("lastName", "Basha");
        obj.put("address", "Campomeal");
        obj.put("city", "Ames");
        obj.put("state", "Iowa");
        obj.put("zip", "50010");
        obj.put("email", "ybasha1@iastate.edu");
        obj.put("password", "pass");
        obj.put("phoneNumber", "515-911-1234");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, obj,
            response -> {
                textView.setText(response.toString());
            },
            error -> {
                textView.setText(error.toString());
            });
        AppController.getInstance().addToRequestQueue(req, tag_object_req);
    }

    public void getRider(View view) {
        TextView textView = (TextView) findViewById(R.id.textview);
        EditText editText = (EditText) findViewById(R.id.getRiderID);
        String id = editText.getText().toString();
        String url = "http://coms-309-030.class.las.iastate.edu:8080/rider/getRider?id=" + id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> textView.setText(response.toString()),
                error -> textView.setText(error.toString())
        );
        AppController.getInstance().addToRequestQueue(req, tag_object_req);
    }

    public void deleteRider(View view){
        TextView textView = (TextView) findViewById(R.id.textview);
        EditText editText = (EditText) findViewById(R.id.getRiderID);
        String id = editText.getText().toString();
        String url = "http://coms-309-030.class.las.iastate.edu:8080/rider/deleteRider?id=" + id;

        StringRequest req = new StringRequest(Request.Method.DELETE, url,
                response -> textView.setText(response),
                error -> textView.setText(error.toString())
        );
        AppController.getInstance().addToRequestQueue(req, tag_string_req);
    }
}