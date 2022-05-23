package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view){
        String username = ((EditText) findViewById(R.id.userNameET)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordET)).getText().toString();

        Intent intent = null;
        if(username == "driver")
            intent = new Intent(this, DriverActivity.class);
        if(username == "rider")
            intent = new Intent(this, RiderActivity.class);
        if(password == "password")
            startActivity(intent);
    }
}