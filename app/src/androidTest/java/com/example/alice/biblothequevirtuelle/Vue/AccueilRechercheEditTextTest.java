package com.example.alice.biblothequevirtuelle.Vue;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alice.biblothequevirtuelle.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AccueilRechercheEditTextTest {

    @Rule
    public ActivityTestRule<Accueil> mActivityTestRule = new ActivityTestRule<>(Accueil.class);

    @Test
    public void accueilConnexionRechercheTest() {

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_Rech), withText("Rechercher")));
        appCompatButton2.perform(scrollTo(), click());

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.etTitre)).perform(typeText("Test")).check(matches(withText("Test")));

    }

}
