package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by Tamas_Kozmer on 7/24/2017.
 */
class GetUsersTest {

    @Mock
    lateinit var mockUserRepository: UserRepository

    lateinit var getUsers: GetUsers

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getUsers = GetUsers(mockUserRepository)
    }

    @Test
    fun testExecute_userListModelWithOneItem_emitListWithOneViewModel() {
        // Given
        val mockSingle = Single.create { e: SingleEmitter<UserListModel>? -> e?.onSuccess(UserListModel(listOf(User(1, "Name", 100, "Image url")))) }

        // When
        `when`(mockUserRepository.getUsers(1, false))
                .thenReturn(mockSingle)

        val resultSingle = getUsers.execute(1, false)

        val testObserver = resultSingle.test()

        testObserver.assertNoErrors()
        testObserver.assertValue { userViewModels: List<UserViewModel> -> userViewModels.size == 1 }
        testObserver.assertValue { userViewModels: List<UserViewModel> ->
            userViewModels.get(0) == UserViewModel(1, "Name", 100, "Image url") }
    }

    @Test
    fun testExecute_userListModelEmpty_emitEmptyList() {
        // Given
        val mockSingle = Single.create { e: SingleEmitter<UserListModel>? -> e?.onSuccess(UserListModel(emptyList())) }

        // When
        `when`(mockUserRepository.getUsers(1, false))
                .thenReturn(mockSingle)

        val resultSingle = getUsers.execute(1, false)

        val testObserver = resultSingle.test()

        testObserver.assertNoErrors()
        testObserver.assertValue { userViewModels: List<UserViewModel> -> userViewModels.isEmpty() }
    }
}