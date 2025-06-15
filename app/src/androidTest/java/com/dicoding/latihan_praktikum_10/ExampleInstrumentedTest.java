package com.dicoding.latihan_praktikum_10;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.dicoding.latihan_praktikum_10.presentation.auth.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLoginFormValidation() {
        // Isi form email & password
        onView(withId(R.id.etEmail)).perform(typeText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("134568"), closeSoftKeyboard());

        // Klik tombol login
        onView(withId(R.id.btnLogin)).perform(click());
    }
}

