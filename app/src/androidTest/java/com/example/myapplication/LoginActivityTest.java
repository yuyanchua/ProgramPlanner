package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.activity.SignUpActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the login activity.
 * Will check that the proper error
 * message is displayed on incorrect username and password.
 * Also checks activity change on proper button presses.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Setup for Espresso intents.
     */
    @Before
    public void setUp() {
        Intents.init();
    }

    /**
     * Test attempting to lig in with an invalid password.
     * Verify that the proper error message is set.
     */
    @Test
    public void testLoginInvalidPass() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextAccountName)).perform(ViewActions.typeText("InvalidName"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(ViewActions.typeText("testPass"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonLogIn))./*perform(ViewActions.scrollTo()).*/perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.errorMessage)).check(ViewAssertions.matches(ViewMatchers.withText("Fields are Empty")));
    }

    /**
     * Teardown intents
     */
    @After
    public void tearDown() {
        Intents.release();
    }

}
