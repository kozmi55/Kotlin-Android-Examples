package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.DetailsModel
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import io.reactivex.Single

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService) {

    fun getUsers(page: Int) = userService.getUsers(page)

    fun getDetails(userId: Long) : Single<DetailsModel> {
        // TODO
        return Single.create { emitter ->
            val detailsModel = DetailsModel(emptyList(), emptyList(), emptyList())
            emitter.onSuccess(detailsModel)
        }
    }
}