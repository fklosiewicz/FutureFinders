package com.example.pff;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class RegistrationTestHundredAccounts {

    @Test
    public void testRegister() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.Register)).perform(click());

        // Try to register 100 individual accounts
            int i = 1;
            while(i <= 100) {
                String temp = "a";
                onView(withId(R.id.textUsername)).perform(typeText(temp + i));
                pressBack();
                onView(withId(R.id.textPassword)).perform(typeText(temp + i));
                pressBack();
                onView(withId(R.id.Create)).perform(click());
                onView(withId(R.id.textUsername)).perform(clearText());
                onView(withId(R.id.textPassword)).perform(clearText());
                i++;
            }
    }

    @Test
    public void testLogin() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        int i = 1;
        while(i <= 100) {
            String temp = "a";
            // Login user
            onView(withId(R.id.Login)).perform(click());
            onView(withHint("User Name")).perform(typeText(temp + i));
            pressBack();
            onView(withHint("Password")).perform(typeText(temp + i));
            pressBack();
            onView(withText("Login")).perform(click());
            onView(withText("Successful Login!")).check(matches(isDisplayed()));
            onView(withText("Okay")).perform(click());

            // Logout user
            onView(withId(R.id.Logout)).perform(click());
            onView(withId(R.id.Account)).check(matches(not(isDisplayed())));
            i++;
        }


    }
}
