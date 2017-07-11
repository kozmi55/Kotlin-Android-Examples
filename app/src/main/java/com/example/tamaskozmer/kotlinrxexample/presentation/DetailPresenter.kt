package com.example.tamaskozmer.kotlinrxexample.presentation

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
class DetailPresenter(private val userRepository: UserRepository) : BasePresenter<DetailView>() {

    fun getDetails(id: Long) {
        view?.showLoading()
        userRepository.getDetails(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    detailsModel ->
                    view?.hideLoading()
                    view?.showDetails(detailsModel)
                },
                {
                    error ->
                    view?.hideLoading()
                    view?.showError()
                })
    }
}