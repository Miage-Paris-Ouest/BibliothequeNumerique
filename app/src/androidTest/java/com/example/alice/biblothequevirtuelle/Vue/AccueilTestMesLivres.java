package com.example.alice.biblothequevirtuelle.Vue;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alice.biblothequevirtuelle.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class AccueilTestMesLivres {

    @Rule
    public ActivityTestRule<Accueil> mActivityTestRule = new ActivityTestRule<>(Accueil.class);

    @Test
    public void accueilTestMesLivres() {
        //ViewInteraction appCompatButton = onView(allOf(withId(R.id.bLivres),withText("Mes livres"),withParent(allOf(withId(R.id.activity_accueil),withParent(withId(android.R.id.content)))),isDisplayed()));
        onView(withText(R.id.bLivres)).perform(click());
        //onView(withId(R.id.mes)).check(matches(withText(R.string.second_tv_welcome)));
    }
}