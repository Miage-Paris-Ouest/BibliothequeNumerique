package com.example.alice.biblothequevirtuelle.Firebase;

/**
 * Created by Kiki on 15/05/2017.
 */
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
public class ReinitMDPTest {
    @Rule
    public ActivityTestRule<ReinitMotDePasse> mActivityTestRule = new ActivityTestRule<>(ReinitMotDePasse.class);

    @Test
    public void accueilReinitMDPTest() {
        onView(ViewMatchers.withId(R.id.etAdresseMail)).perform(typeText("Test")).check(matches(withText("Test")));

    }
}
