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
    val state: MutableLiveData<State> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var initialized = false

    fun init(id: Long, forced: Boolean = false) {
        if (!initialized) {
            getDetails(id, forced)
            initialized = true
        }
    }

    fun getDetails(id: Long, forced: Boolean) {
        state.value = if (details.value == null) State.INITIAL_LOADING else State.REFRESHING
        compositeDisposable.addAll(
            getDetails.execute(id, forced)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler())
                .subscribe({ detailsModel ->
                    state.value = State.LOADED
                    details.value = detailsModel
                },
                    {
                        state.value = State.ERROR
                    })
        )
    }

    enum class State {
        INITIAL_LOADING, REFRESHING, LOADED, ERROR
    }
}