package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AppController;
import com.example.myapplication.Const;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private String tag_string_req = "string_req";
    private String tag_object_req = "object_req";
    private String tag_array_req = "array_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }

    public void stringRequest(View view){
        String url = "https:api.androidhive.info/volley/string_response.html";
        TextView textView = (TextView) findViewById(R.id.textview);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d(TAG, response);
                    textView.setText(response);
                },
                error -> {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    textView.setText("Error!");
                }
        );
        AppController.getInstance().addToRequestQueue(req, tag_string_req);
    }

    public void jsonObjectRequest(View view){
        TextView textView = (TextView) findViewById(R.id.textview);
        String url = "https://d3070f82-5b4d-4c45-88f6-e4c712d30a0a.mock.pstmn.io/create";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, response.toString());
                    textView.setText(response.toString());
                },
                error -> {
                    VolleyLog.d(TAG, "Error " + error.getMessage());
                    textView.setText("Error!");
                }
        );
        AppController.getInstance().addToRequestQueue(req, tag_object_req);
    }

    public void jsonArrayRequest(View view){
        TextView textView = (TextView) findViewById(R.id.textview);
        String url = "https:api.androidhive.info/volley/person_array.json";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, response.toString());
                    textView.setText(response.toString());
                },
                error -> {
                    VolleyLog.d(TAG, "Error " + error.getMessage());
                    textView.setText("Error!");
                }
        );
        AppController.getInstance().addToRequestQueue(req, tag_array_req);
    }

    public void imageRequest(View view){
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(Const.URL_IMAGE, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap() != null) imageView.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error " + error.getMessage());
            }
        });
    }


    public void postRequest(View view) throws JSONException {
        TextView textView = (TextView) findViewById(R.id.textview);
        String url = "https://10.0.2.2/driver/registerDriver";
        JSONObject obj = new JSONObject();
        obj.put("name", new String("Hello"));
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, obj,
                response -> {
                    Log.d(TAG, response.toString());
                    textView.setText("SUCCESS");
                },
                error -> {
                    VolleyLog.d(TAG, "Error " + error.getMessage());
                    textView.setText("Error!");
                }
        ) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(req, "json_obj_req");

        }
}