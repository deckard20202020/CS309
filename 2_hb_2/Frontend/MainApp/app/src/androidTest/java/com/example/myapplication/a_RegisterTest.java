package com.example.myapplication;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;

// NEVER EVER RUN THIS STANDALONE
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class a_RegisterTest {
    private static final int SIMULATED_DELAY_MS = 8000;
    public static int createdRiderId = 0;
    public static int createdDriverId = 0;
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testRegisterDriver(){
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.riderProfileButton)).perform(click());
        onView(withId(R.id.editTextFirstName)).perform(typeText("driver"), closeSoftKeyboard());
        onView(withId(R.id.editTextLastName)).perform(typeText("last"), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("driver@email.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("Password123!"), closeSoftKeyboard());
        onView(withId(R.id.editTextPhone)).perform(typeText("515-123-1234"), closeSoftKeyboard());
        onView(withId(R.id.editTextAddress)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.editTextCity)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.editTextState)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.editTextZip)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.registerDriverButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        try { createdDriverId = MainActivity.accountObj.getInt("id"); }catch(Exception e){}
        onView(withId(R.id.signOutButton)).perform(click());
    }

    @Test
    public void testRegisterRider(){
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registeringRiderButton)).perform(click());
        onView(withId(R.id.editTextFirstName)).perform(typeText("rider"), closeSoftKeyboard());
        onView(withId(R.id.editTextLastName)).perform(typeText("last"), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("rider@email.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("Password123!"), closeSoftKeyboard());
        onView(withId(R.id.editTextPhone)).perform(typeText("515-123-1234"), closeSoftKeyboard());
        onView(withId(R.id.editTextAddress)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.editTextCity)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.editTextState)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.editTextZip)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.registerRiderButton)).perform(click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        try { createdRiderId = MainActivity.accountObj.getInt("id"); }catch(Exception e){}
        onView(withId(R.id.signOutButton)).perform(click());
    }
}