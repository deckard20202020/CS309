package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class f_OngoingTripTest {
    private static final int SIMULATED_DELAY_MS = 2500;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void driverOngoingTripTest(){
        // Sign in
        String email = "driver2@gmail.com";
        String password = "abc";
        onView(withId(R.id.usernameInput)).perform(typeText(email));
        onView(withId(R.id.passwordInput)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // View all my trips
        onView(withId(R.id.driverRidesButton)).perform(click());

        // View first trip
        Espresso.onView(ViewMatchers.withText("View")).perform(ViewActions.click());

        // Start the trip
        Espresso.onView(ViewMatchers.withText("Start")).perform(ViewActions.click());
        try { Thread.sleep(6000); } catch (InterruptedException e) { }
    }

    @Test
    public void riderOngoingTripTest(){
        // Sign in
        String email = "rider3@gmail.com";
        String password = "abc";
        onView(withId(R.id.usernameInput)).perform(typeText(email));
        onView(withId(R.id.passwordInput)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // View all my trips
        onView(withId(R.id.RiderTripsListButton)).perform(click());

        // View ongoing trip
        Espresso.onView(ViewMatchers.withText("View")).perform(ViewActions.click());
        try { Thread.sleep(6000); } catch (InterruptedException e) { }
    }
}
