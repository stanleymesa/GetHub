package com.example.gethub.ui.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gethub.R
import com.example.gethub.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }

    @Test
    fun runApp() {
        onView(withId(R.id.rv_user)).perform(swipeUp(), swipeUp(), swipeDown())
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.fab_detail)).perform(click())
        onView(withId(R.id.frame_followers)).perform(click())
        onView(withId(R.id.viewpager)).perform(swipeLeft())
        onView(withId(R.id.rv_followers_following)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_detail)).perform(click())
        onView(withId(R.id.frame_followers)).perform(click())
        onView(withId(R.id.rv_followers_following)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_detail)).perform(click())
        onView(withId(R.id.detail_home_menu)).perform(click())
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        try {
            onView(withText(R.string.dark_mode)).perform(click())
        } catch (e: NoMatchingViewException) {
            onView(withText(R.string.light_mode)).perform(click())
        }
        onView(withId(R.id.rv_user)).perform(swipeUp(), swipeUp(), swipeDown())
        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withId(R.id.rv_favorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(R.string.select_all)).perform(click())
        onView(withId(R.id.favorite_delete_menu)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}