package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
    }

    public void sayHello(View view){
        EditText nameInputLabel = (EditText) findViewById(R.id.nameInputLabel);
        String name = nameInputLabel.getText().toString();
        TextView nameTextView = findViewById(R.id.nameTextView);
        if(name == "")
            nameTextView.setText("What is your name?");
        else {
            nameTextView.setText("HELLO " + name);
        }
    }
}