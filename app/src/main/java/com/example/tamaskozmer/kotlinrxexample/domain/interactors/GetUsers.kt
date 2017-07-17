package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import io.reactivex.Single

/**
 * Created by Tamas_Kozmer on 7/14/2017.
 */
class GetUsers(private val userRepository: UserRepository) {

    // TODO Not the best practice probably
    fun execute(page: Int, forced: Boolean) : Single<List<UserViewModel>> {
        val usersList = userRepository.getUsers(page, forced)
        return usersList.flatMap { userListModel: UserListModel? ->
            val items = userListModel?.items ?: emptyList()
            val viewModels = items.map { UserViewModel(it.userId, it.displayName, it.reputation, it.profileImage) }
            Single.just(viewModels)
        }
    }
}