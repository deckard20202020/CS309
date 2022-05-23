package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.endpoints.Endpoints;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.app.AppController;
import com.example.myapplication.rider.HomePage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * allows for users to change the settings of their account
 */
public class ProfileSettings extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText phoneNumber;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zip;
    private EditText email;
    ImageView profilePic;

    private String uriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        firstName = findViewById(R.id.editTextFirstName2);
        lastName = findViewById(R.id.editTextLastName2);
        password = findViewById(R.id.editTextPassword2);
        phoneNumber = findViewById(R.id.editTextPhone2);
        address = findViewById(R.id.editTextAddress2);
        city = findViewById(R.id.editTextCity2);
        state = findViewById(R.id.editTextState2);
        zip = findViewById(R.id.editTextZip2);
        email = findViewById(R.id.editTextEmail2);
        profilePic = findViewById(R.id.profilePic);

        setPreviousDetails();

        try {
            HelperFunctions.setProfilePic(profilePic);
        }catch(Exception e){
            Log.e("error", e.toString());
        }
    }

    /**
     * applies the changes to the account and saves them to the database
     * @param view activity that is referencing this method
     */
    public void saveChanges(View view) {
        boolean x = HelperFunctions.verifyNotNull(firstName.getText().toString(), lastName.getText().toString(),
                email.getText().toString(), address.getText().toString(), city.getText().toString(),
                state.getText().toString(), zip.getText().toString(), password.getText().toString(),
                phoneNumber.getText().toString(), findViewById(R.id.statusTV));
        boolean y = HelperFunctions.verifyParametersMet(password.getText().toString(), email.getText().toString(),
                phoneNumber.getText().toString(), findViewById(R.id.statusTV));

        if (x && y) {
            changeProfileRequest(getDetails());
            changeProfilePicRequest();
        }
    }

    private void setPreviousDetails(){
        try {
            firstName.setText(MainActivity.accountObj.getString("firstName"));
            lastName.setText(MainActivity.accountObj.getString("lastName"));
            password.setText(MainActivity.accountObj.getString("password"));
            phoneNumber.setText(MainActivity.accountObj.getString("phoneNumber"));
            address.setText(MainActivity.accountObj.getString("address"));
            city.setText(MainActivity.accountObj.getString("city"));
            state.setText(MainActivity.accountObj.getString("state"));
            zip.setText(MainActivity.accountObj.getString("zip"));
            email.setText(MainActivity.accountObj.getString("email"));
        } catch(JSONException e){}
    }

    private JSONObject getDetails(){
        JSONObject newUserDetails = new JSONObject();
        try {
            newUserDetails.put("firstName", firstName.getText().toString());
            newUserDetails.put("lastName", lastName.getText().toString());
            newUserDetails.put("password", password.getText().toString());
            newUserDetails.put("phoneNumber", phoneNumber.getText().toString());
            newUserDetails.put("address", address.getText().toString());
            newUserDetails.put("city", city.getText().toString());
            newUserDetails.put("state", state.getText().toString());
            newUserDetails.put("zip", zip.getText().toString());
            newUserDetails.put("email", email.getText().toString());
        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Encountered exception " + e, Toast.LENGTH_LONG).show());
        }
        return newUserDetails;
    }

    private void changeProfileRequest(JSONObject newUserDetails){
        try {
            String url = Endpoints.EditUserUrl + MainActivity.accountObj.get("id");
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, newUserDetails,
                response -> {
                    try {
                        if (!response.isNull("firstName")) {
                            MainActivity.accountObj = response;
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show());
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Encountered exception " + e, Toast.LENGTH_LONG).show());
                    }
                },
                error -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Encountered error " + error, Toast.LENGTH_LONG).show()));
            AppController.getInstance().addToRequestQueue(req, "post_object_tag");
        } catch(JSONException e) {}
    }

    private void changeProfilePicRequest(){
        try {
            String url = Endpoints.SetProfilePictureUrl + MainActivity.accountObj.get("id") + "&path=" + uriString;
            StringRequest req = new StringRequest(Request.Method.PUT, url,
                    response -> Log.d("success", "changed profile picture"),
                    error -> Log.d("success", "failed to changed profile picture"));
            AppController.getInstance().addToRequestQueue(req, "string_req");
        }
        catch(Exception e){
            Log.e("error", e.toString());
        }
    }

    /**
     * sets profile picture
     * @param view view that is referencing this method
     */
    public void tempSetProfilePicture(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                profilePic.setImageURI(uri);
                uriString = uri.toString();
            } catch (Exception e) {
                Log.e("error", e.toString());
            }
        }
    }

    public void onBackPressed() {
        try {
            Intent intent = null;
            if (!(MainActivity.accountObj.isNull("adriver")) && MainActivity.accountObj.getBoolean("adriver")) {
                intent = new Intent(this, com.example.myapplication.driver.HomePage.class);
            } else if (!MainActivity.accountObj.isNull("arider") && MainActivity.accountObj.getBoolean("arider"))
                intent = new Intent(this, com.example.myapplication.rider.HomePage.class);
            startActivity(intent);
        }catch(Exception e){
            Log.e("error", e.toString());
        }
    }


}