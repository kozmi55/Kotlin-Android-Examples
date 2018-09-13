package com.example.tamaskozmer.kotlinrxexample.presentation.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.DetailsViewData
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getDetails: GetDetails,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val details: MutableLiveData<DetailsViewData> = MutableLiveData()
    val showInitialLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<Boolean> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var initialized = false

    fun init(id: Long, forced: Boolean = false) {
        if (!initialized) {
            getDetails(id, forced)
            initialized = true
        }
    }

    fun getDetails(id: Long, forced: Boolean) {
        showInitialLoading.value = true
        compositeDisposable.addAll(
            getDetails.execute(id, forced)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler())
                .subscribe({ detailsModel ->
                    showInitialLoading.value = false
                    details.value = detailsModel
                },
                    { error ->
                        showInitialLoading.value = false
                        showError.value = true
                    })
        )
    }
}