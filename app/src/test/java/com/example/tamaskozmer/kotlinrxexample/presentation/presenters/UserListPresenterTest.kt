package com.example.tamaskozmer.kotlinrxexample.presentation.presenters

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.presentation.view.UserListView
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import com.example.tamaskozmer.kotlinrxexample.testutil.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

/**
 * Created by Tamas_Kozmer on 7/21/2017.
 */
class UserListPresenterTest {

    @Mock
    lateinit var mockGetUsers: GetUsers

    @Mock
    lateinit var mockView: UserListView

    lateinit var userListPresenter: UserListPresenter

    lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        userListPresenter = UserListPresenter(mockGetUsers, testSchedulerProvider)
    }

    @Test
    fun testGetUsers_errorCase_showError() {
        // Given
        val error = "Test error"
        val single: Single<List<UserViewModel>> = Single.create {
            emitter ->
            emitter.onError(Exception(error))
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()

        testScheduler.triggerActions()

        // Then
        verify(mockView).hideLoading()
        verify(mockView).showError()
    }

    @Test
    fun testGetUsers_successCaseFirstPage_clearList() {
        // Given
        val users = listOf(UserViewModel(1, "Name", 1000, ""))
        val single: Single<List<UserViewModel>> = Single.create {
            emitter ->
            emitter.onSuccess(users)
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()

        testScheduler.triggerActions()

        // Then
        verify(mockView).clearList()
    }

    @Test
    fun testGetUsers_successCaseMultipleTimes_clearListOnlyOnce() {
        // Given
        val users = listOf(UserViewModel(1, "Name", 1000, ""))
        val single: Single<List<UserViewModel>> = Single.create {
            emitter ->
            emitter.onSuccess(users)
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()
        userListPresenter.getUsers()

        testScheduler.triggerActions()

        // Then
        verify(mockView).clearList()
        verify(mockView, times(2)).hideLoading()
        verify(mockView, times(2)).addUsersToList(users)
    }

    @Test
    fun testGetUsers_forcedSuccessCaseMultipleTimes_clearListEveryTime() {
        // Given
        val users = listOf(UserViewModel(1, "Name", 1000, ""))
        val single: Single<List<UserViewModel>> = Single.create {
            emitter ->
            emitter.onSuccess(users)
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers(forced = true)
        userListPresenter.getUsers(forced = true)

        testScheduler.triggerActions()

        // Then
        verify(mockView, times(2)).clearList()
        verify(mockView, times(2)).hideLoading()
        verify(mockView, times(2)).addUsersToList(users)
    }

    @Test
    fun testOnScrollChanged_offsetReachedAndLoading_dontRequestNextPage() {
        callOnScrollChanged(5, 1)

        // Then
        verify(mockGetUsers, times(1))
                .execute(ArgumentMatchers.anyInt(), ArgumentMatchers.anyBoolean())
    }

    @Test
    fun testOnScrollChanged_offsetReachedAndNotLoading_requestNextPage() {
        callOnScrollChanged(5, 3)

        // Then
        verify(mockGetUsers, times(2))
                .execute(ArgumentMatchers.anyInt(), ArgumentMatchers.anyBoolean())
    }

    @Test
    fun testOnScrollChanged_lastItemReachedAndLoading_showLoading() {
        callOnScrollChanged(10, 1)

        // Then
        verify(mockView).showLoading()
    }

    private fun callOnScrollChanged(lastVisibleItemPosition: Int, secondsDelay: Long) {
        getUsersWithLoadingDelay()
        testScheduler.advanceTimeBy(secondsDelay, TimeUnit.SECONDS)

        userListPresenter.onScrollChanged(lastVisibleItemPosition, 10)
    }

    private fun getUsersWithLoadingDelay() {
        // Given
        val users = listOf(UserViewModel(1, "Name", 1000, ""))
        val single: Single<List<UserViewModel>> = Single.create {
            emitter ->
            emitter.onSuccess(users)
        }

        val delayedSingle = single.delay(2, TimeUnit.SECONDS, testScheduler)

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(delayedSingle)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()
    }
}