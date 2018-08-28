package com.example.tamaskozmer.kotlinrxexample.view.fragments

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.mocks.di.components.DaggerMockApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.mocks.di.modules.MockApplicationModule
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.testutil.RecyclerViewMatcher
import com.example.tamaskozmer.kotlinrxexample.view.activities.MainActivity
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever


/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
@RunWith(AndroidJUnit4::class)
class UserListFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Mock
    private lateinit var mockUserRepository: UserRepository

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as CustomApplication

        val testComponent = DaggerMockApplicationComponent.builder()
                .mockApplicationModule(MockApplicationModule(mockUserRepository))
                .build()
        app.component = testComponent
    }

    @Test
    fun testRecyclerViewShowingCorrectItems() {
        mockRepoUsers(1)

        activityRule.launchActivity(Intent())

        checkNameOnPosition(0, "User 1")
        checkNameOnPosition(2, "User 3")
    }

    @Test
    fun testRecyclerViewShowingCorrectItemsAfterScroll() {
        mockRepoUsers(1)

        activityRule.launchActivity(Intent())

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))

        checkNameOnPosition(8, "User 9")
    }

    @Test
    fun testRecyclerViewShowingCorrectItemsAfterPagination() {
        mockRepoUsers(1)
        mockRepoUsers(2)

        activityRule.launchActivity(Intent())

        // Trigger pagination
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))

        // Scroll to a position on the next page
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(25))

        // Check if view is showing the correct text
        checkNameOnPosition(25, "User 26")
    }

    private fun checkNameOnPosition(position: Int, expectedName: String) {
        Espresso.onView(withRecyclerView(R.id.recyclerView).atPositionOnView(position, R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText(expectedName)))
    }

    @Test
    fun testOpenDetailsOnItemClick() {
        mockRepoUsers(1)

        activityRule.launchActivity(Intent())

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        // Check if we see the new recyclerview with the clicked items name shown in the first element
        Espresso.onView(withRecyclerView(R.id.detailsRecyclerView).atPositionOnView(0, R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText("User 1")))
    }

    @Test
    fun testFirstPageErrorShowErrorView() {
        mockRepoError(1)

        activityRule.launchActivity(Intent())

        Espresso.onView(ViewMatchers.withId(R.id.errorView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSecondPageErrorShowToast() {
        mockRepoUsers(1)
        mockRepoError(2)

        activityRule.launchActivity(Intent())

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))

        onView(ViewMatchers.withText("Error loading data"))
                .inRoot(withDecorView(Matchers.not(Matchers.`is`(activityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(isDisplayed()))
    }

    private fun mockRepoUsers(page: Int) {
        val users = (1..20L).map {
            val number = (page - 1) * 20 + it
            User(it, "User $number", number * 100, "")
        }

        val mockSingle = Single.create<UserListModel> { emitter: SingleEmitter<UserListModel> ->
            val userListModel = UserListModel(users)
            emitter.onSuccess(userListModel)
        }

        whenever(mockUserRepository.getUsers(page, false)).thenReturn(mockSingle)
    }

    private fun mockRepoError(page: Int) {
        val mockSingle = Single.create<UserListModel> { emitter: SingleEmitter<UserListModel> ->
            emitter.onError(Throwable("Error"))
        }

        whenever(mockUserRepository.getUsers(page, false)).thenReturn(mockSingle)
    }
}