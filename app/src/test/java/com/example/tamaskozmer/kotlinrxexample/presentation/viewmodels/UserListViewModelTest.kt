package com.example.tamaskozmer.kotlinrxexample.presentation.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import com.example.tamaskozmer.kotlinrxexample.testutil.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGetUsers: GetUsers

    lateinit var userListViewModel: UserListViewModel

    lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        userListViewModel = UserListViewModel(mockGetUsers, testSchedulerProvider)
    }

    @Test
    fun testGetUsers_errorCase_showError() {
        // Given
        val error = "Test error"
        val single: Single<List<UserViewData>> = Single.create { emitter ->
            emitter.onError(Exception(error))
        }

        // When
        whenever(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListViewModel.init()

        testScheduler.triggerActions()

        // Then
        assertTrue(userListViewModel.showError.value == true)
    }

    @Test
    fun testOnScrollChanged_offsetReachedAndLoading_dontRequestNextPage() {
        callOnScrollChanged(5, 1)

        // Then
        Mockito.verify(mockGetUsers, Mockito.times(1))
            .execute(ArgumentMatchers.anyInt(), ArgumentMatchers.anyBoolean())
    }

    @Test
    fun testOnScrollChanged_offsetReachedAndNotLoading_requestNextPage() {
        callOnScrollChanged(5, 3)

        // Then
        Mockito.verify(mockGetUsers, Mockito.times(2))
            .execute(ArgumentMatchers.anyInt(), ArgumentMatchers.anyBoolean())
    }

    @Test
    fun testOnScrollChanged_lastItemReachedAndLoading_showLoading() {
        callOnScrollChanged(10, 1)

        // Then
        assertTrue(userListViewModel.showLoading.value == true)
    }

    private fun callOnScrollChanged(lastVisibleItemPosition: Int, secondsDelay: Long) {
        getUsersWithLoadingDelay()
        testScheduler.advanceTimeBy(secondsDelay, TimeUnit.SECONDS)

        userListViewModel.onScrollChanged(lastVisibleItemPosition, 10)
    }

    private fun getUsersWithLoadingDelay() {
        // Given
        val users = listOf(UserViewData(1, "Name", 1000, ""))
        val single: Single<List<UserViewData>> = Single.create { emitter ->
            emitter.onSuccess(users)
        }

        val delayedSingle = single.delay(2, TimeUnit.SECONDS, testScheduler)

        // When
        whenever(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(
            delayedSingle
        )

        userListViewModel.init()
    }
}