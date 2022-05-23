package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.app.AppController;
import com.example.myapplication.endpoints.Endpoints;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class z_Cleanup {
    private static final int SIMULATED_DELAY_MS = 2000;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void deleteUser(){
        String url = Endpoints.DeleteUserUrl + a_RegisterTest.createdRiderId;
        StringRequest req = new StringRequest(Request.Method.DELETE, url,
            response -> { },
            error -> { }
        );
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        url = Endpoints.DeleteUserUrl + a_RegisterTest.createdDriverId;
        req = new StringRequest(Request.Method.DELETE, url,
            response -> { },
            error -> { }
        );
        AppController.getInstance().addToRequestQueue(req, "post_object_tag");
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

    }
}