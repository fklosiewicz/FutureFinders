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

import com.example.pff.design.User;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class ProceedAsMemberUserTest {

    //Member selects 1 state and 1 indicator
    @Test
    public void MemberResultsOneState() {

        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        //create a test User instance
        User testUser = new User("testUserName", "testUserPassword");
        //populate the IndicatorActivity's activeUser field, simulating logged in state
        activityScenario.onActivity(activity -> {
            activity.activeUser = testUser;
        });
        //select the first state
        onView(withId(R.id.CT)).perform(click());
        //press Next: Indicators button
        onView(withId(R.id.Next)).perform(click());
        //Indicator page select 1 indicator
        onView(withId(R.id.entertainment)).perform(click());
        //proceed to results page
        onView(withId(R.id.see_results)).perform(click());
        onView(withId(R.id.textView6)).check(matches(withText("CT")));
        onView(withId(R.id.textView7)).check(matches(withText("43")));
    }

    //Guest selects 3 states, 5 indicators
    //CT, CA, AL
    //safety, healthcare, air and water quality, education, cost of living
    @Test
    public void MemberResultsThreeStates() {
        //launch MainActivity
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        User testUser = new User("testUserName", "testUserPassword");
        //populate the IndicatorActivity's activeUser field, simulating logged in state
        activityScenario.onActivity(activity -> {
            activity.activeUser = testUser;
        });
        //check that user is still member
        activityScenario.onActivity(activity -> {
            assertNotNull(activity.activeUser);
        });

        //select the first state
        onView(withId(R.id.CT)).perform(click());
        //select the second state
        onView(withId(R.id.AK)).perform(click());
        //select third state
        onView(withId(R.id.AL)).perform(click());
        //press Next
        onView(withId(R.id.Next)).perform(click());

        //check that user is still member
        activityScenario.onActivity(activity -> {
            assertNotNull(activity.activeUser);
        });
        //select indicators
        onView(withId(R.id.tax_bracket)).perform(click());
        onView(withId(R.id.healthcare)).perform(click());
        onView(withId(R.id.air_water)).perform(click());
        onView(withId(R.id.education)).perform(click());
        onView(withId(R.id.living_index)).perform(click());
        //proceed to results page
        onView(withId(R.id.see_results)).perform(click());

        //check that user is still member
        activityScenario.onActivity(activity -> {
            assertNotNull(activity.activeUser);
        });
        //safety, healthcare, air and water quality, education, cost of living
        //First state results
        onView(withId(R.id.textView6)).check(matches(withText("CT")));
        onView(withId(R.id.textView7)).check(matches(withText("5")));
        onView(withId(R.id.textView8)).check(matches(withText("9")));
        onView(withId(R.id.textView9)).check(matches(withText("30")));
        onView(withId(R.id.textView10)).check(matches(withText("6")));
        onView(withId(R.id.textView22)).check(matches(withText("127.7")));
        //Second state results
        onView(withId(R.id.textView11)).check(matches(withText("AK")));
        onView(withId(R.id.textView12)).check(matches(withText("49")));
        onView(withId(R.id.textView13)).check(matches(withText("36")));
        onView(withId(R.id.textView14)).check(matches(withText("50")));
        onView(withId(R.id.textView15)).check(matches(withText("49")));
        onView(withId(R.id.textView23)).check(matches(withText("129.9")));
        //third
        onView(withId(R.id.textView16)).check(matches(withText("AL")));
        onView(withId(R.id.textView17)).check(matches(withText("44")));
        onView(withId(R.id.textView18)).check(matches(withText("51")));
        onView(withId(R.id.textView19)).check(matches(withText("33")));
        onView(withId(R.id.textView20)).check(matches(withText("47")));
        onView(withId(R.id.textView24)).check(matches(withText("89.3")));
    }

}