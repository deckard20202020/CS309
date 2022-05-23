package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.rider.RegistrationPage;

/**
 * allows users to choose between registering a rider or a driver
 */
public class RegistrationOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_option);
    }

    /**
     * proceeds to activity to register a rider
     * @param view activity that is referencing this method
     */
    public void riderRegistration(View view){
        Intent intent = new Intent(this, com.example.myapplication.rider.RegistrationPage.class);
        startActivity(intent);
    }

    /**
     * proceeds to activity to register a driver
     * @param view activity that is referencing this method
     */
    public void driverRegistration(View view){
        Intent intent = new Intent(this, com.example.myapplication.driver.RegistrationPage.class);
        startActivity(intent);
    }
}
