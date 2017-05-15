package com.example.alice.biblothequevirtuelle.Firebase;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alice.biblothequevirtuelle.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Kiki on 15/05/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CreationTest {
    @Rule
    public ActivityTestRule<CreationCompte> mActivityTestRule = new ActivityTestRule<>(CreationCompte.class);

    @Test
    public void editTextCreationTest() {
        onView(withId(R.id.etMailInscription)).perform(typeText("Test")).check(matches(withText("Test")));
    }


}
