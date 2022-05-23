package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.myapplication.driver.completetrip.TripCompleted;
import com.example.myapplication.rider.completetrip.RateDriver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class g_RateUserTest {
    private static final int SIMULATED_DELAY_MS = 3000;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void driverRateRiderTest() {
        // Sign in
        String email = "driver2@gmail.com";
        String password = "abc";
        onView(withId(R.id.usernameInput)).perform(typeText(email));
        onView(withId(R.id.passwordInput)).perform(typeText(password));
        onView(withId(R.id.passwordInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // View all trips
        onView(withId(R.id.driverRidesButton)).perform(click());

        // View first trip
        onView(withText("View")).perform(click());

        // Start the trip
        onView(withText("Start")).perform(click());

        Intent i = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), TripCompleted.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InstrumentationRegistry.getInstrumentation().getTargetContext().startActivity(i);

        // Select the first (and only) rider to rate
        Espresso.onView(ViewMatchers.withText("Rate")).perform(ViewActions.click());

        // Write review and submit
        onView(withId(R.id.driverCommentingRiderET)).perform(typeText("Driver review for rider"));
        onView(withId(R.id.driverCommentingRiderET)).perform(closeSoftKeyboard());
        onView(withId(R.id.driverSubmitRatingButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // Finish trip
        onView(withId(R.id.driverFinishTripButton)).perform(click());

        // Sign out
        onView(withId(R.id.signOutButton)).perform(click());

        // Rider signs in
        onView(withId(R.id.usernameInput)).perform(typeText("rider3@gmail.com"));
        onView(withId(R.id.passwordInput)).perform(typeText("abc"));
        onView(withId(R.id.passwordInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Rider views rating
        onView(withId(R.id.riderReviewsButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
    }

    @Test
    public void riderRateDriverTest() {
        // Sign in
        String email = "rider3@gmail.com";
        String password = "abc";
        onView(withId(R.id.usernameInput)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.passwordInput)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // View all my trips
        onView(withId(R.id.RiderTripsListButton)).perform(click());

        // View ongoing trip
        onView(withText("View")).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        Intent i = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), RateDriver.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InstrumentationRegistry.getInstrumentation().getTargetContext().startActivity(i);

        // Write review and submit
        onView(withId(R.id.riderCommentingDriverET)).perform(typeText("Driver review for rider"), closeSoftKeyboard());
        onView(withId(R.id.riderSubmitRatingButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // Sign out
        onView(withId(R.id.signOutButton)).perform(click());

        // Driver signs in
        onView(withId(R.id.usernameInput)).perform(typeText("driver2@gmail.com"));
        onView(withId(R.id.passwordInput)).perform(typeText("abc"));
        onView(withId(R.id.passwordInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Driver views rating
        onView(withId(R.id.driverReviewsButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
    }
}
