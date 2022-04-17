package com.example.pff;

import static androidx.test.espresso.Espresso.getIdlingResources;
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
import static org.junit.Assert.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;

import android.view.LayoutInflater;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ActivityScenario;

import com.example.pff.design.User;

import java.util.ArrayList;
import android.content.Context;


public class ExplanationTest {
    @Test
    public void explanationTest(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
//        onView(withId(R.id.Register)).perform(click());
//        onView(withId(R.id.textUsername)).perform(typeText("a1b2c3"));
//        pressBack();
//        onView(withId(R.id.textPassword)).perform(typeText("password"));
//        pressBack();
//        onView(withId(R.id.Create)).perform(click());
//        pressBack();

        onView(withId(R.id.Login)).perform(click());
        onView(withHint("User Name")).perform(typeText("1234567"));
        pressBack();
        onView(withHint("Password")).perform(typeText("1234567"));
        pressBack();
        onView(withText("Login")).perform(click());
        onView(withText("Okay")).perform(click());

        onView(withId(R.id.VA)).perform(click());

        onView((withId(R.id.Next))).perform(click());

        onView(withId(R.id.question1)).perform(click());
        String explanation1 = MyApp.getRes().getString(R.string.explanation1);
        onView(withId(R.id.explanation_text)).check(matches(withText(explanation1)));
        onView(withId(R.id.explanation_close)).perform(click());

        onView(withId(R.id.question2)).perform(click());
        String explanation2 = MyApp.getRes().getString(R.string.explanation2);
        onView(withId(R.id.explanation_text)).check(matches(withText(explanation2)));
        onView(withId(R.id.explanation_close)).perform(click());

        onView(withId(R.id.question3)).perform(click());
        String explanation3 = MyApp.getRes().getString(R.string.explanation3);
        onView(withId(R.id.explanation_text)).check(matches(withText(explanation3)));
        onView(withId(R.id.explanation_close)).perform(click());

        onView(withId(R.id.question4)).perform(click());
        String explanation4 = MyApp.getRes().getString(R.string.explanation4);
        onView(withId(R.id.explanation_text)).check(matches(withText(explanation4)));
        onView(withId(R.id.explanation_close)).perform(click());

        onView(withId(R.id.question5)).perform(click());
        String explanation5 = MyApp.getRes().getString(R.string.explanation5);
        onView(withId(R.id.explanation_text)).check(matches(withText(explanation5)));
        onView(withId(R.id.explanation_close)).perform(click());

    }


}