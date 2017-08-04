package com.example.tamaskozmer.kotlinrxexample.presentation.presenters

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.presentation.view.DetailView
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
class DetailPresenter(
        private val getDetails: GetDetails,
        private val schedulerProvider: SchedulerProvider) : BasePresenter<DetailView>() {

    fun getDetails(id: Long, forced: Boolean = false) {
        view?.showLoading()
        getDetails.execute(id, forced)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler())
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