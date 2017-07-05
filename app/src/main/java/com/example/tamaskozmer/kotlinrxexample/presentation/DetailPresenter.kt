package com.example.tamaskozmer.kotlinrxexample.presentation

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.view.DetailView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
class DetailPresenter(private val userRepository: UserRepository) : BasePresenter<DetailView>() {

    fun getDetails(id: Long) {
        userRepository.getDetails(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    detailsModel ->
                    view?.showDetails(detailsModel)
                },
                {
                    error -> view?.showError()
                })
    }
}