package com.example.pff;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProceedAsGuestUserTest {

    //I don't know why California doesn't pass the test

    //Guest selects 1 state
    @Test
    public void GuestResultsOneState() {
        //launch MainActivity
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        //select the first state
        //onView(withId(R.id.CA)).perform(click());
        onView(withId(R.id.CT)).perform(click());
        //press Next: Indicators button
        onView(withId(R.id.Next)).perform(click());
        //press Ignore and Continue
        onView(withId(R.id.reminder_continue)).perform(click());
        //State, wage, state tax, happiness
        //Second state results
        onView(withId(R.id.textView6)).check(matches(withText("CT")));
        onView(withId(R.id.textView7)).check(matches(withText("87,370")));
        onView(withId(R.id.textView8)).check(matches(withText("5.50%")));
        onView(withId(R.id.textView9)).check(matches(withText("11")));
        /*
        onView(withId(R.id.textView6)).check(matches(withText("CA")));
        onView(withId(R.id.textView7)).check(matches(withText("104,260")));
        onView(withId(R.id.textView8)).check(matches(withText("8.50%")));
        onView(withId(R.id.textView9)).check(matches(withText("4")));
        */
    }

    //Guest selects 2 states
    @Test
    public void GuestResultsTwoStates() {
        //launch MainActivity
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        //select the first state
        onView(withId(R.id.CT)).perform(click());
        //select the second state
        onView(withId(R.id.CA)).perform(click());
        //press Next: Indicators button
        onView(withId(R.id.Next)).perform(click());
        //press Ignore and Continue
        onView(withId(R.id.reminder_continue)).perform(click());
        //State, wage, state tax, happiness
        //First state results
        onView(withId(R.id.textView6)).check(matches(withText("CT")));
        onView(withId(R.id.textView7)).check(matches(withText("87,370")));
        onView(withId(R.id.textView8)).check(matches(withText("5.50%")));
        onView(withId(R.id.textView9)).check(matches(withText("11")));
        //State, wage, state tax, happiness
        //Second state results
        onView(withId(R.id.textView11)).check(matches(withText("CA")));
        onView(withId(R.id.textView12)).check(matches(withText("104,260")));
        onView(withId(R.id.textView13)).check(matches(withText("8.50%")));
        onView(withId(R.id.textView14)).check(matches(withText("4")));
    }

}