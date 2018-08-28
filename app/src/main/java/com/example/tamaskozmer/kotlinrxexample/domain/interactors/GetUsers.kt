package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import io.reactivex.Single
import javax.inject.Inject

class GetUsers @Inject constructor(private val userRepository: UserRepository) {

    fun execute(page: Int, forced: Boolean) : Single<List<UserViewModel>> {
        val usersList = userRepository.getUsers(page, forced)
        return usersList.map { userListModel: UserListModel? ->
            val items = userListModel?.items ?: emptyList()
            items.map { UserViewModel(it.userId, it.displayName, it.reputation, it.profileImage) }
        }
    }
}