package com.example.tamaskozmer.kotlinrxexample.presentation.presenters

/**
 * Created by tamaskozmer on 3/23/17.
 *
 * Helper class for MVP pattern
 * Converted from JAVA code
 *
 * @param <T> - View class
</T> */
abstract class BasePresenter<T> {

    var view: T? = null
        private set

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    val isViewAttached: Boolean
        get() = view != null
}