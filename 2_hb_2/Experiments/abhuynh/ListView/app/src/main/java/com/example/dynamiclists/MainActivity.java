package com.example.dynamiclists;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.listView);

        ArrayList<String> list;
        list = new ArrayList<>(Arrays.asList("A, B, C, D, E, F".split(",")));
        listview.setAdapter(new CustomAdapter(list, getApplicationContext()) );
    }
}