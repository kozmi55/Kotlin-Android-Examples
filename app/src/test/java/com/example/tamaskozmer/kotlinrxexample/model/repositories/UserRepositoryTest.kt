package com.example.tamaskozmer.kotlinrxexample.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.CalendarWrapper
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import com.example.tamaskozmer.kotlinrxexample.util.PreferencesHelper
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Tamas_Kozmer on 7/24/2017.
 */
class UserRepositoryTest {

    @Mock
    lateinit var mockUserService: UserService

    @Mock
    lateinit var mockUserDao: UserDao

    @Mock
    lateinit var mockConnectionHelper: ConnectionHelper

    @Mock
    lateinit var mockPreferencesHelper: PreferencesHelper

    @Mock
    lateinit var mockCalendarWrapper: CalendarWrapper

    @Mock
    lateinit var mockUserCall: Call<UserListModel>

    @Mock
    lateinit var mockUserResponse: Response<UserListModel>

    lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepository(mockUserService, mockUserDao, mockConnectionHelper, mockPreferencesHelper, mockCalendarWrapper)
    }

    @Test
    fun testGetUsers_isOnlineReceivedEmptyList_emitEmptyList() {
        // Given
        val userListModel = UserListModel(emptyList())

        // When
        setUpMocks(userListModel, true)
        val testObserver = userRepository.getUsers().test()

        // Then
        testObserver.assertNoErrors()
        testObserver.assertValue { userListModelResult: UserListModel -> userListModelResult.items.isEmpty() }
        verify(mockUserDao).insertAll(userListModel.items)
    }

    private fun setUpMocks(modelFromUserService: UserListModel, isOnline: Boolean) {
        `when`(mockConnectionHelper.isOnline()).thenReturn(isOnline)
        `when`(mockCalendarWrapper.getCurrentTimeInMillis()).thenReturn(1000 * 60 * 60 * 12 + 1)
        `when`(mockPreferencesHelper.loadLong("last_update_page_1")).thenReturn(0)

        `when`(mockUserService.getUsers()).thenReturn(mockUserCall)
        `when`(mockUserCall.execute()).thenReturn(mockUserResponse)
        `when`(mockUserResponse.body()).thenReturn(modelFromUserService)
        `when`(mockUserDao.getUsers(1)).thenReturn(emptyList())
    }
}