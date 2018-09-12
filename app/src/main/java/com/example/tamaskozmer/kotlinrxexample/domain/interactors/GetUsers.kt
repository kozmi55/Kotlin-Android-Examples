package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import io.reactivex.Single
import javax.inject.Inject

class GetUsers @Inject constructor(private val userRepository: UserRepository) {

    fun execute(page: Int, forced: Boolean): Single<List<UserViewData>> {
        val usersList = userRepository.getUsers(page, forced)
        return usersList.map { userListModel: UserListModel? ->
            val items = userListModel?.items ?: emptyList()
            items.map { UserViewData(it.userId, it.displayName, it.reputation, it.profileImage) }
        }
    }
}