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
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class UsernameTest {

    @Test
    public void testRegister() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        // run register
        onView(withId(R.id.Register)).perform(click());
        onView(withId(R.id.textUsername)).perform(typeText("userTest"));
        pressBack();
        onView(withId(R.id.textPassword)).perform(typeText("passTest"));
        pressBack();
        onView(withId(R.id.Create)).perform(click());
        pressBack();

        // test that login checks username
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("nonUser"));
        pressBack();
        onView(withHint("Password")).perform(typeText("passTest"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("This user does not exist.")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());

        // test that login checks password
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("userTest"));
        pressBack();
        onView(withHint("Password")).perform(typeText("wrongPass"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("This user does not exist.")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());

        // test that login successfully logs in a registered user
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("userTest"));
        pressBack();
        onView(withHint("Password")).perform(typeText("passTest"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("Successful Login!")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());

        // check that logout works
        onView(withId(R.id.Logout)).perform(click());
        onView(withId(R.id.Account)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testOldSearches() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        // show that past search results are not available to guests
        onView(withId(R.id.Account)).check(matches(not(isDisplayed())));

        // login
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("userTest"));
        pressBack();
        onView(withHint("Password")).perform(typeText("passTest"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("Successful Login!")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());

        // check that five buttons are available
        onView(withId(R.id.Account)).perform(click());
        onView(withId(R.id.option1)).check(matches(isDisplayed()));
        onView(withId(R.id.option2)).check(matches(isDisplayed()));
        onView(withId(R.id.option3)).check(matches(isDisplayed()));
        onView(withId(R.id.option4)).check(matches(isDisplayed()));
        onView(withId(R.id.option5)).check(matches(isDisplayed()));

        // check that no searches are saved for new user
        onView(withId(R.id.option1)).perform(click());
        onView(withText("Please select a valid prior search")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());
        pressBack();

        // perform search (assumed to work)
        onView(withId(R.id.NJ)).perform(click());
        onView(withId(R.id.NY)).perform(click());
        onView(withId(R.id.Next)).perform(click());
        onView(withId(R.id.wage)).perform(click());
        onView(withId(R.id.happy)).perform(click());
        onView(withId(R.id.see_results)).perform(click());

        // check that correct search is saved to button 1
        pressBack();
        pressBack();
        onView(withId(R.id.Account)).perform(click());
        onView(withId(R.id.option1)).perform(click());
        onView(withId(R.id.textView2)).check(matches(withText("Salary")));
        onView(withId(R.id.textView3)).check(matches(withText("Happiness")));
        onView(withId(R.id.textView6)).check(matches(withText("NJ")));
        onView(withId(R.id.textView11)).check(matches(withText("NY")));
    }

    @Test
    public void testChangePassword() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        // login
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("userTest"));
        pressBack();
        onView(withHint("Password")).perform(typeText("passTest"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("Successful Login!")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());

        // go to account page
        onView(withId(R.id.Account)).perform(click());
        onView(withId(R.id.textPassword2)).perform(typeText("newPass"));
        pressBack();
        onView(withId(R.id.update)).perform(click());
        onView(withId(R.id.Logout)).perform(click());

        // check that old login info no longer works
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("userTest"));
        pressBack();
        onView(withHint("Password")).perform(typeText("passTest"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("This user does not exist.")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());

        // check that new login info does work
        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("userTest"));
        pressBack();
        onView(withHint("Password")).perform(typeText("newPass"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("Successful Login!")).check(matches(isDisplayed()));
        onView(withText("Okay")).perform(click());
    }

}