package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    String firstName, lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
    }

    public void sayHello(View view){
        EditText firstNameInputLabel = (EditText) findViewById(R.id.firstNameInputLabel);
        EditText lastNameInputLabel = (EditText) findViewById(R.id.lastNameInputLabel);
        firstName = firstNameInputLabel.getText().toString();
        lastName = lastNameInputLabel.getText().toString();
        TextView nameTextView = findViewById(R.id.nameTextView);
        if(firstName == "" && lastName == "")
            nameTextView.setText("Please enter your name.");
        else {
            nameTextView.setText("Hello " + firstName + " " + lastName);
        }
    }

    public void previousPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(firstName, lastName);
        startActivity(intent);
    }

    public void nextPage(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}