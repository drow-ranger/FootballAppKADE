package com.deonico.footballapp_kade

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.deonico.footballapp_kade.R.id.*
import com.deonico.footballapp_kade.activity.MatchHomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchHomeActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MatchHomeActivity::class.java)

    @Test
    fun testAppBehavior(){
        Thread.sleep(2000)

        //Prev Match
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner))
                .perform(ViewActions.click())
        onView(withText("German Bundesliga"))
                .perform(ViewActions.click())
        Thread.sleep(2000)
        onView(withId(recyclerView))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(recyclerView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(14, click()))
        Thread.sleep(2000)
        onView(withId(action_favourite))
                .check(matches(isDisplayed()))
        onView(withId(action_favourite))
                .perform(click())
        onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Prev Match

        //Next Match
        onView(withId(bottomNav))
                .check(matches(isDisplayed()))
        onView(withId(action_next))
                .perform(click())
        Thread.sleep(2000)
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner))
                .perform(ViewActions.click())
        onView(withText("Italian Serie A")).perform(ViewActions.click())
        Thread.sleep(2000)
        onView(withId(recyclerView))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(recyclerView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(2000)
        onView(withId(action_favourite))
                .check(matches(isDisplayed()))
        onView(withId(action_favourite))
                .perform(click())
        onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Next Match

        //Favorite Match
        onView(withId(bottomNav))
                .check(matches(isDisplayed()))
        onView(withId(action_favorite_match))
                .perform(click())
        onView(withId(recyclerView))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(action_favourite))
                .check(matches(isDisplayed()))
        onView(withId(action_favourite))
                .perform(click())
        onView(withText(R.string.remove_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        onView(withId(swipeRefresh)).perform(ViewActions.swipeDown())
        //End

        //Team
        Thread.sleep(2000)
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext())
        onView(withText(R.string.teams))
                .perform(click())
        //End Team

        //Favorite Team 1
        Thread.sleep(2000)
        onView(withId(spinnerLeague))
                .check(matches(isDisplayed()))
        onView(withId(spinnerLeague))
                .perform(ViewActions.click())
        onView(withText("Dutch Eredivisie")).perform(ViewActions.click())
        Thread.sleep(2000)
        onView(withId(rvTeams))
                .check(matches(isDisplayed()))
        onView(withId(rvTeams))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rvTeams))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))
        Thread.sleep(2000)
        onView(withId(action_favourite))
                .check(matches(isDisplayed()))
        onView(withId(action_favourite))
                .perform(click())
        onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Favorite Team 1

        //Favorite Team 2
        Thread.sleep(2000)
        onView(withId(spinnerLeague))
                .check(matches(isDisplayed()))
        onView(withId(spinnerLeague))
                .perform(ViewActions.click())
        onView(withText("English Premier League")).perform(ViewActions.click())
        Thread.sleep(2000)
        onView(withId(rvTeams))
                .check(matches(isDisplayed()))
        onView(withId(rvTeams))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rvTeams))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))
        Thread.sleep(2000)
        onView(withId(action_favourite))
                .check(matches(isDisplayed()))
        onView(withId(action_favourite))
                .perform(click())
        onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Favorite Team 2

        //Remove Favorite Team
        onView(withId(bottomNav))
                .check(matches(isDisplayed()))
        onView(withId(action_favourites))
                .perform(click())
        onView(withId(recyclerView))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(action_favourite))
                .check(matches(isDisplayed()))
        onView(withId(action_favourite))
                .perform(click())
        onView(withText(R.string.remove_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        onView(withId(swipeRefresh)).perform(ViewActions.swipeDown())
        //End

    }
}