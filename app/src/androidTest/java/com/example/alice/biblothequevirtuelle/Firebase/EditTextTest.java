package com.example.alice.biblothequevirtuelle.Firebase;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alice.biblothequevirtuelle.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EditTextTest {

    @Rule
    public ActivityTestRule<Authentification> mActivityTestRule = new ActivityTestRule<>(Authentification.class);

    @Test
    public void editTextTest() {
        onView(ViewMatchers.withId(R.id.etAdresseMail)).perform(typeText("test")).check(matches(withText("test")));

    }

}
