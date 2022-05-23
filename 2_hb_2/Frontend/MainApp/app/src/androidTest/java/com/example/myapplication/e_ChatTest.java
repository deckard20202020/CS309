package com.example.myapplication;

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
public class e_ChatTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void driverChat() {
        // Sign in
        String email = "driver2@gmail.com";
        String password = "abc";
        Espresso.onView(ViewMatchers.withId(R.id.usernameInput)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // View all my trips
        Espresso.onView(ViewMatchers.withId(R.id.driverRidesButton)).perform(ViewActions.click());

        // View first trip
        Espresso.onView(ViewMatchers.withText("View")).perform(ViewActions.click());

        // Chat with rider
        Espresso.onView(ViewMatchers.withText("Chat")).perform(ViewActions.click());

        // Write and send message
        Espresso.onView(ViewMatchers.withId(R.id.messageET)).perform(ViewActions.typeText("Hello rider. - Driver"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sendMessageButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS * 4); } catch (InterruptedException e) { }

    }

    @Test
    public void riderChat() {
        // Sign in
        String email = "rider3@gmail.com";
        String password = "abc";
        Espresso.onView(ViewMatchers.withId(R.id.usernameInput)).perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }

        // View all my trips
        Espresso.onView(ViewMatchers.withId(R.id.RiderTripsListButton)).perform(ViewActions.click());

        // Chat with driver
        Espresso.onView(ViewMatchers.withText("Chat")).perform(ViewActions.click());

        // Write and send message
        Espresso.onView(ViewMatchers.withId(R.id.messageET)).perform(ViewActions.typeText("Hello driver. - Rider"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sendMessageButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS * 4); } catch (InterruptedException e) { }
    }
}
