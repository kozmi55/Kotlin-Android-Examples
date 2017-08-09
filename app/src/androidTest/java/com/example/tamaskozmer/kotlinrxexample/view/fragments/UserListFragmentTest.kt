package com.example.tamaskozmer.kotlinrxexample.view.fragments

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.mocks.di.components.DaggerMockApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.mocks.di.modules.MockApplicationModule
import com.example.tamaskozmer.kotlinrxexample.testutil.RecyclerViewMatcher
import com.example.tamaskozmer.kotlinrxexample.view.activities.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
@RunWith(AndroidJUnit4::class)
class UserListFragmentTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as CustomApplication

        val testComponent = DaggerMockApplicationComponent.builder()
                .mockApplicationModule(MockApplicationModule())
                .build()
        app.component = testComponent

        activityRule.launchActivity(Intent())
    }

    @Test
    fun testRecyclerViewShowingCorrectItems() {
        checkNameOnPosition(0, "User 1")
        checkNameOnPosition(2, "User 3")
    }

    @Test
    fun testRecyclerViewShowingCorrectItemsAfterScroll() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))

        checkNameOnPosition(8, "User 9")
    }

    @Test
    fun testRecyclerViewShowingCorrectItemsAfterPagination() {
        // Trigger pagination
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))

        // Scroll to a position on the next page
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))

        // Check if view is showing the correct text
        checkNameOnPosition(15, "User 16")
    }

    private fun checkNameOnPosition(position: Int, expectedName: String) {
        Espresso.onView(withRecyclerView(R.id.recyclerView).atPositionOnView(position, R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText(expectedName)))
    }

    @Test
    fun testOpenDetailsOnItemClick() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        // Check if we see the new recyclerview with the clicked items name shown in the first element
        Espresso.onView(withRecyclerView(R.id.detailsRecyclerView).atPositionOnView(0, R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText("User 1")))
    }
}