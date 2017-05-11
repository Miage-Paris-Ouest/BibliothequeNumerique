package com.example.alice.biblothequevirtuelle.Vue;

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

/**
 * Created by Kiki on 10/05/2017.
 */

//Tests fonctionnels pour Acceuil
@RunWith(AndroidJUnit4.class)
public class AjouterTestFonct {
    @Rule
    public ActivityTestRule<Ajouter> activityTestRule = new ActivityTestRule<>(Ajouter.class);

    @Test
    public void validEditText() {
        onView(ViewMatchers.withId(R.id.etISBN)).perform(typeText("test")).check(matches(withText("test")));
    }
}