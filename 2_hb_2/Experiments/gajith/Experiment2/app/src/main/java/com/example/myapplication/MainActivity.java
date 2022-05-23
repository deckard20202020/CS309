package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    public static int flag = 0;
    public static String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }

    public void disable(View v) {
        View mv = findViewById(R.id.button);
        mv.setEnabled(false);
        Button b = (Button) mv;
        b.setText("Button Off");
    }

    // Enter text and prints it out
    public void handleInput(View v) {
        //Takes user input
        EditText t = findViewById(R.id.Source);
        s = t.getText().toString();

        if (s.equals("Name") || s.equals("name") || s.equals("")) {
            flag = 0;
            Toast.makeText(this, "Didn't work", Toast.LENGTH_LONG).show();
            ((TextView) findViewById(R.id.output)).setText("Please type your name");
        } else {
            ((TextView) findViewById(R.id.output)).setText("Hello! " + s + "\nPlease click next to continue");
            flag = 1;

        }
    }

    public void launchSettings(View v) {
        if (flag == 1) {
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra("Cool", s);
            startActivity(i);
        } else {
            Toast.makeText(this, "Please type your name", Toast.LENGTH_LONG).show();
        }

    }
}