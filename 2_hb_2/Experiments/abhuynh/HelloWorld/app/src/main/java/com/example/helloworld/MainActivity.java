package com.example.helloworld;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextPage(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}