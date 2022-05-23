package com.example.myapplication.driver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.app.AppController;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.myapplication.endpoints.Endpoints;
import com.example.myapplication.HelperFunctions;
import com.example.myapplication.*;
/**
 * where the driver registers for an account
 */
public class RegistrationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_registration_page);
    }

    /**
     * takes the fields provided and creates a driver from them if valid
     * @param view the activity that is referencing this method
     * @throws JSONException throws JSON Exception
     */
    public void registerDriver(View view) throws JSONException {
        TextView tv = findViewById(R.id.regStatusTextView);
        String firstName = ((EditText) findViewById(R.id.editTextFirstName)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.editTextLastName)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
        String address = ((EditText) findViewById(R.id.editTextAddress)).getText().toString();
        String city = ((EditText) findViewById(R.id.editTextCity)).getText().toString();
        String state = ((EditText) findViewById(R.id.editTextState)).getText().toString();
        String zip = ((EditText) findViewById(R.id.editTextZip)).getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("firstName", firstName);
        obj.put("lastName", lastName);
        obj.put("address", address);
        obj.put("city", city);
        obj.put("state", state);
        obj.put("zip", zip);
        obj.put("email", email);
        obj.put("password", password);
        obj.put("phoneNumber", phoneNumber);

        boolean x = HelperFunctions.verifyNotNull(firstName, lastName, email, address, city, state, zip, password,
                phoneNumber, tv);

        boolean y = HelperFunctions.verifyParametersMet(password, email, phoneNumber, tv);

        if (x && y) {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Endpoints.DriverRegUrl, obj,
                    response -> {
                        if (!response.isNull("firstName")) {
                            Intent intent = new Intent(this, HomePage.class);
                            MainActivity.accountObj = response;
                            startActivity(intent);
                        } else {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error processing" + obj, Toast.LENGTH_LONG).show());
                        }
                    },
                    error -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show()));
            AppController.getInstance().addToRequestQueue(req, "post_object_tag");
        }
    }
}