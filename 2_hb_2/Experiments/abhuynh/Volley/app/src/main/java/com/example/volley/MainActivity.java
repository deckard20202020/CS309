package com.example.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.volley.app.AppController;
import com.example.volley.net_utils.Const;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    public void deleteRequest(View view){
        String url = "http://coms-309-030.class.las.iastate.edu:8080/driver/deleteDriver?id=3";
        TextView textView = (TextView) findViewById(R.id.textview);

        StringRequest req = new StringRequest(Request.Method.DELETE, url,
            response -> textView.setText("Successful delete!"),
            error -> textView.setText("Error deleting!")
        );
        AppController.getInstance().addToRequestQueue(req, tag_string_req);
    }
    public void stringRequest(View view){
        String url = "https://41096e03-1605-4363-a912-57afa92f86c7.mock.pstmn.io/bumboString";
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
        String url = "http://coms-309-030.class.las.iastate.edu:8080/driver/getDriver?id=5";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
            response -> textView.setText(response.toString()),
            error -> textView.setText("Error!")
        );
        AppController.getInstance().addToRequestQueue(req, tag_object_req);
    }

    public void jsonArrayRequest(View view){
        TextView textView = (TextView) findViewById(R.id.textview);
        String url = "https://41096e03-1605-4363-a912-57afa92f86c7.mock.pstmn.io/bumboArray";
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

    public void jsonPostRequest(View view) throws JSONException {
        TextView textView = (TextView) findViewById(R.id.textview);
        String url = "http://coms-309-030.class.las.iastate.edu:8080/driver/registerDriver/";

        StringRequest req = new StringRequest(Request.Method.POST, url,
            response -> textView.setText(response),
            error -> textView.setText(error.toString()))
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    return params;
                }
                @Override
                public byte[] getBody() {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("firstName", new String("Matt"));
                        obj.put("lastName", new String("Sinwell"));
                        obj.put("address", "1234 lol St.");
                        obj.put("city", "Ames");
                        obj.put("state", "Iowa");
                        obj.put("zip", "50000");
                        obj.put("email", "g@s.com");
                        obj.put("phoneNumber", "515-911-1234");
                        String objString =  obj.toString();

                        return objString.toString().getBytes("utf-8");
                    }
                    catch (Exception e){
                        return null;
                    }
                }
            };
        AppController.getInstance().addToRequestQueue(req, tag_object_req);
    }
}