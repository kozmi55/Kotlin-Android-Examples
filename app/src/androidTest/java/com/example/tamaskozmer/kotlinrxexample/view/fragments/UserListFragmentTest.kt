package com.example.tamaskozmer.kotlinrxexample.view.fragments

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.DaggerMockApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.R
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

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
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
        Espresso.onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText("User 1")))

        Espresso.onView(withRecyclerView(R.id.recyclerView).atPositionOnView(3, R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText("User 4")))
    }
}