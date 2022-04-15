package com.example.pff;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import androidx.test.core.app.ActivityScenario;
import com.example.pff.design.User;
import org.junit.Test;
import java.util.ArrayList;

public class SelectionLimitTest {

    @Test
    public void testGuestStates(){
        //Launch MainActivity
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        //select the first state
        onView(withId(R.id.CT)).perform(click());
        //Verify that the states list size is 1, which means guest was allowed the first state
        activityScenario.onActivity(activity -> {
                assertThat(activity.states.size(),is(1));
           });

        //select the second state
        onView(withId(R.id.CA)).perform(click());
        //Verify that the states list size is 2, which means guest was allowed the second state
        activityScenario.onActivity(activity -> {
            assertThat(activity.states.size(),is(2));
        });

        //try to select a third state
        onView(withId(R.id.WA)).perform(click());
        //Verify that the guest received the limit message
        onView(withText("Maximum 2 states for guest.\nTo " +
                "select up to 3 states, please register.")).check(matches(isDisplayed()));
        //Verify that the states list size is still 2, which means guest was not
        //allowed the third state
        activityScenario.onActivity(activity -> {
            assertThat(activity.states.size(),is(2));
        });
    }

    @Test
    public void testMemberStates(){
        //Launch MainActivity
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        //create a test User instance
        User testUser = new User("testUserName", "testUserPassword");
        //populate the MainActivity's activeUser field, simulating logged in state
        activityScenario.onActivity(activity -> {
            activity.activeUser = testUser;
        });

        //select the first state
        onView(withId(R.id.CT)).perform(click());
        //Verify that the states list size is 1, which means member was allowed the first state
        activityScenario.onActivity(activity -> {
            assertThat(activity.states.size(),is(1));
        });

        //select the second state
        onView(withId(R.id.CA)).perform(click());
        //Verify that the states list size is 2, which means member was allowed the second state
        activityScenario.onActivity(activity -> {
            assertThat(activity.states.size(),is(2));
        });

        //select the third state
        onView(withId(R.id.WA)).perform(click());
        //Verify that the states list size is 3, which means member was allowed the third state
        activityScenario.onActivity(activity -> {
            assertThat(activity.states.size(),is(3));
        });

        //try to select a fourth state
        onView(withId(R.id.NJ)).perform(click());
        //Verify that the member received the limit message
        onView(withText("Maximum 3 states for members.")).check(matches(isDisplayed()));
        //Verify that the states list size is still 3, which means member was not
        //allowed the fourth state
        activityScenario.onActivity(activity -> {
            assertThat(activity.states.size(),is(3));
        });
    }

    @Test
    public void testMemberIndicators(){

        //Launch IndicatorActivity
        ActivityScenario<IndicatorActivity> activityScenario = ActivityScenario.launch(IndicatorActivity.class);

        //create a test User instance
        User testUser = new User("testUserName", "testUserPassword");
        //populate the IndicatorActivity's activeUser field, simulating logged in state
        activityScenario.onActivity(activity -> {
            activity.activeUser = testUser;
        });
        activityScenario.onActivity(activity -> {
            activity.indicators = new ArrayList();
        });

        //select the first indicator
        onView(withId(R.id.wage)).perform(click());
        //Verify that the indicators list size is 1, which means member was allowed the first indicator
        activityScenario.onActivity(activity -> {
            assertThat(activity.indicators.size(),is(1));
        });

        //select the second indicator
        onView(withId(R.id.happy)).perform(click());
        //Verify that the indicators list size is 2, which means member was allowed the second indicator
        activityScenario.onActivity(activity -> {
            assertThat(activity.indicators.size(),is(2));
        });

        //select the third indicator
        onView(withId(R.id.tax_rate)).perform(click());
        //Verify that the indicators list size is 3, which means member was allowed the third indicator
        activityScenario.onActivity(activity -> {
            assertThat(activity.indicators.size(),is(3));
        });

        //select the fourth indicator
        onView(withId(R.id.entertainment)).perform(click());
        //Verify that the indicators list size is 4, which means member was allowed the fourth indicator
        activityScenario.onActivity(activity -> {
            assertThat(activity.indicators.size(),is(4));
        });

        //select the fifth indicator
        onView(withId(R.id.education)).perform(click());
        //Verify that the indicators list size is 5, which means member was allowed the fifth indicator
        activityScenario.onActivity(activity -> {
            assertThat(activity.indicators.size(),is(5));
        });

        //try to select a sixth indicator
        onView(withId(R.id.healthcare)).perform(click());
        //Verify that the member received the limit message
        onView(withText("Maximum 5 indicators for members to choose.\n")).check(matches(isDisplayed()));
        //Verify that the indicators list size is still 5, which means member was not
        //allowed the sixth indicator
        activityScenario.onActivity(activity -> {
            assertThat(activity.indicators.size(),is(5));
        });
    }
}
