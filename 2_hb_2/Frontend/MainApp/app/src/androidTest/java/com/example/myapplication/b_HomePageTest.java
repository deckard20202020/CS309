package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class b_HomePageTest {
    private static final int SIMULATED_DELAY_MS = 2000;
    public static int createdRiderId = 0;
    public static int createdDriverId = 0;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void driverProfileTest(){
        String email = "driver@email.com";
        String password = "Password123!";
        Espresso.onView(ViewMatchers.withId(R.id.usernameInput)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        Espresso.onView(ViewMatchers.withId(R.id.driverProfileButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Save Changes")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextFirstName2)).perform(ViewActions.replaceText("Steve"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextFirstName2)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.saveChangesButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Save Changes")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void riderProfileTest(){
        String email = "rider@email.com";
        String password = "Password123!";
        Espresso.onView(ViewMatchers.withId(R.id.usernameInput)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        Espresso.onView(ViewMatchers.withId(R.id.riderProfileButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Save Changes")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextFirstName2)).perform(ViewActions.replaceText("Bobby"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextFirstName2)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.saveChangesButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Save Changes")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void signOutRiderTest(){
        String email = "rider@email.com";
        String password = "Password123!";
        Espresso.onView(ViewMatchers.withId(R.id.usernameInput)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        Espresso.onView(ViewMatchers.withId(R.id.signOutButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Sign In")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void signOutDriverTest(){
        String email = "driver@email.com";
        String password = "Password123!";
        Espresso.onView(ViewMatchers.withId(R.id.usernameInput)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.passwordInput)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        try { Thread.sleep(SIMULATED_DELAY_MS); } catch (InterruptedException e) { }
        Espresso.onView(ViewMatchers.withId(R.id.signOutButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Sign In")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
