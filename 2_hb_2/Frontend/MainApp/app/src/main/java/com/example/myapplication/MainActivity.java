package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.endpoints.Endpoints;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myapplication.app.AppController;
import com.example.myapplication.rider.HomePage;

/**
 * start page of the app; allows for signing in or for registering of an account
 */
public class MainActivity extends AppCompatActivity {

    /**
     * if signed in, this holds all the information about the user as an object
     */
    public static JSONObject accountObj;

    /**
     * if signed in, this is the user's email
     */
    public static String accountEmail;

    /**
     * if signed in, this is the user's id
     */
    public static int accountId;

    /**
     * holds the username and password of the account locally if "keep signed in" was checked upon log in
     */
    public SharedPreferences prefs;
    /**
     * used for checking if the user clicked "keep signed in" when they last logged in
     */
    public boolean isLoggedIn;

    private EditText usernameField;
    private EditText passwordField;
    private CheckBox checkbox;
    private ProgressBar signInLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("name", MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        setContentView(R.layout.activity_main);

        usernameField = findViewById(R.id.usernameInput);
        passwordField = findViewById(R.id.passwordInput);
        checkbox = findViewById(R.id.staySignedInCheckBox);
        signInLoader = findViewById(R.id.signInLoader);
        signInLoader.setVisibility(View.INVISIBLE);


        if(isLoggedIn){
            String email = prefs.getString("email", "");
            String password = prefs.getString("password", "");
            usernameField.setText(email);
            passwordField.setText(password);
            checkbox.setChecked(true);
            signInLoader.setVisibility(View.VISIBLE);
            signInRequest(email, password);
        }
    }

    /**
     * the user signs in using their email and password
     * @param view activity that is referencing this method
     */
    public void signIn(View view) {
        String email = ((EditText) findViewById(R.id.usernameInput)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();
        signInRequest(email, password);
    }

    /**
     * the user makes an account, either a driver or a rider
     * @param view activity that is referencing this method
     */
    public void register(View view){
        Intent intent = new Intent(this, RegistrationOptions.class);
        startActivity(intent);
    }

    private void signInRequest(String email, String password){
        String url = Endpoints.LoginUrl + email + "&password=" + password;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
            response -> {
                try {
                    accountObj = response;
                    accountEmail = response.getString("email");
                    accountId = response.getInt("id");
                    Intent intent = null;
                    if(!accountObj.isNull("firstName")){
                        if(!(accountObj.isNull("adriver")) && accountObj.getBoolean("adriver")) {
                            intent = new Intent(this, com.example.myapplication.driver.HomePage.class);
                        }
                        else if (!accountObj.isNull("arider") && accountObj.getBoolean("arider")) {
                            intent = new Intent(this, com.example.myapplication.rider.HomePage.class);
                        }
                        else if (!(accountObj.isNull("anAdmin")) && accountObj.getBoolean("anAdmin")) {
                            intent = new Intent(this, com.example.myapplication.admin.HomePage.class);
                        }
                        if(((CheckBox) findViewById(R.id.staySignedInCheckBox)).isChecked()){
                            SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();
                        }
                        startActivity(intent);
                    }
                    else {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error processing" + url, Toast.LENGTH_LONG).show());
                    }
                }
                catch(JSONException e){
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Exception: " + e, Toast.LENGTH_LONG).show());
                }
            },
            error ->  runOnUiThread(()->Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show()));
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");
    }

}