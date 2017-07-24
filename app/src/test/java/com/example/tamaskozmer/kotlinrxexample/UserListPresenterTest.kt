package com.example.tamaskozmer.kotlinrxexample

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.UserListView
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Tamas_Kozmer on 7/21/2017.
 */
class UserListPresenterTest {

    // TODO Fix the problem with TestScheduler and then change this to TestScheduler and write more tests
    @Rule @JvmField
    val immediateSchedulerRule = ImmediateSchedulerRule()

    @Mock
    lateinit var mockGetUsers: GetUsers

    @Mock
    lateinit var mockView: UserListView

    lateinit var userListPresenter: UserListPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        userListPresenter = UserListPresenter(mockGetUsers)
    }

    @Test
    fun testGetUsers_errorCase_showError() {
        // Given
        val error = "Test error"
        val single: Single<List<UserViewModel>> = Single.create {
            emitter -> emitter?.onError(Exception(error))
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()

        // Then
        verify(mockView).hideLoading()
        verify(mockView).showError()
    }

    @Test
    fun testGetUsers_successCaseFirstPage_clearList() {
        // Given
        val users = listOf(UserViewModel(1, "Name", 1000, ""))
        val single: Single<List<UserViewModel>> = Single.create {
            emitter -> emitter?.onSuccess(users)
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()

        // Then
        verify(mockView).clearList()
    }

    @Test
    fun testGetUsers_successCaseMultipleTimes_clearListOnlyOnce() {
        // Given
        val users = listOf(UserViewModel(1, "Name", 1000, ""))
        val single: Single<List<UserViewModel>> = Single.create {
            emitter -> emitter?.onSuccess(users)
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers()
        userListPresenter.getUsers()

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
            emitter -> emitter?.onSuccess(users)
        }

        // When
        `when`(mockGetUsers.execute(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(single)

        userListPresenter.attachView(mockView)
        userListPresenter.getUsers(forced = true)
        userListPresenter.getUsers(forced = true)

        // Then
        verify(mockView, times(2)).clearList()
        verify(mockView, times(2)).hideLoading()
        verify(mockView, times(2)).addUsersToList(users)
    }
}