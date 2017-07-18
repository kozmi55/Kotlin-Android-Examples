package com.example.tamaskozmer.kotlinrxexample.presentation.presenters

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.presentation.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
class DetailPresenter(private val getDetails: GetDetails) : BasePresenter<DetailView>() {

    fun getDetails(id: Long) {
        view?.showLoading()
        getDetails.execute(id)
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
                    view?.showError(error.localizedMessage)
                })
    }
}