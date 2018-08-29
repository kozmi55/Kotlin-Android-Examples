package com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter

class FakeUserRepository : UserRepository {

    override fun getUsers(page: Int, forced: Boolean): Single<UserListModel> {
        val users = (1..10L).map {
            val number = (page - 1) * 10 + it
            User(it, "User $number", number * 100, "")
        }

        return Single.create<UserListModel> { emitter: SingleEmitter<UserListModel> ->
            val userListModel = UserListModel(users)
            emitter.onSuccess(userListModel)
        }
    }
}