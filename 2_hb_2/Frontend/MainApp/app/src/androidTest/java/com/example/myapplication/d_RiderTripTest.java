package com.example.myapplication;

//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.equalTo;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.app.AppController;
import com.example.myapplication.driver.createtrip.ConfirmTrip;
import com.example.myapplication.endpoints.Endpoints;
import com.example.myapplication.rider.searchtrip.SearchTripPlace;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@LargeTest
public class d_RiderTripTest {

    private static final int SIMULATED_DELAY_MS = 3500;
    private static String email = "rider1@gmail.com";
    private static String password = "abc";

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void viewTrip()
    {
        onView(withId(R.id.usernameInput)).perform(typeText(email));
        onView(withId(R.id.passwordInput)).perform(typeText(password));
        onView(withId(R.id.passwordInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.RiderTripsListButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.riderTripsListView)).check(matches(ViewMatchers.isDisplayed()));
    }


    @Test
    public void searchTrip(){
        onView(withId(R.id.usernameInput)).perform(typeText(email));
        onView(withId(R.id.passwordInput)).perform(typeText(password));
        onView(withId(R.id.passwordInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.findRideButton)).perform(click());
        onView(withId(R.id.selectTimeButton)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 10));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.selectDateButton)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,5,10));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.chooseStartButton)).perform(click());
        SearchTripPlace.originAddress = "2229 Lincoln Way, Ames, IA 50011, USA";
        SearchTripPlace.destAddress = "Des Moines, IA, USA";
        SearchTripPlace.distance = 59.079;
        SearchTripPlace.durationHours = 0;
        SearchTripPlace.durationMinutes = 40;
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.searchPageLocationButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.searchRideButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        String url = null;
        try {
            url = Endpoints.AddRiderToTripUrl+ ConfirmTrip.tripInfo.getInt("id")+
                    "&riderId="+MainActivity.accountObj.getInt("id") + "&riderOriginAddress=" +
                    SearchTripPlace.originAddress + "&riderDestAddress=" + SearchTripPlace.destAddress;
        }catch (Exception e) {}

        StringRequest req = new StringRequest(Request.Method.PUT, url,
                response -> {},
                error -> { }
        );
        AppController.getInstance().addToRequestQueue(req, "string_req");
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        onView(withId(R.id.riderTripsListView)).check(matches(ViewMatchers.isDisplayed()));
    }

}
