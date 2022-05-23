package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import android.view.View;
//import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static org.hamcrest.Matchers.equalTo;

import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.driver.createtrip.ConfirmTrip;
import com.example.myapplication.driver.createtrip.SelectTripPlace;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@LargeTest
public class c_DriverTripTest {

    private static final int SIMULATED_DELAY_MS = 2000;
    private static String email = "driver1@gmail.com";
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
        onView(withId(R.id.driverRidesButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.driverTripsListView)).check(matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void createTrip(){
        onView(withId(R.id.usernameInput)).perform(typeText(email));
        onView(withId(R.id.passwordInput)).perform(typeText(password));
        onView(withId(R.id.passwordInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.createRideButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.selectTimeButton)).perform(click());
        Espresso.onView(ViewMatchers.withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 15));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.selectDateButton)).perform(click());
        onView(ViewMatchers.withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,5,12));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.chooseStartButton)).perform(click());
        SelectTripPlace.originAddress = "2229 Lincoln Way, Ames, IA 50011, USA";
        SelectTripPlace.destAddress = "Des Moines, IA, USA";
        SelectTripPlace.distance = 59.079;
        SelectTripPlace.durationHours = 0;
        SelectTripPlace.durationMinutes = 40;
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        onView(withId(R.id.confirmRideLocationButton)).perform(click());
        ConfirmTrip.radius = 10;
        ConfirmTrip.maxRiders = 4;
        ConfirmTrip.rate = 2.5;
        onView(withId(R.id.confirmRideButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        Espresso.onView(ViewMatchers.withText("Sign out")).check(matches(ViewMatchers.isDisplayed()));
    }

}
