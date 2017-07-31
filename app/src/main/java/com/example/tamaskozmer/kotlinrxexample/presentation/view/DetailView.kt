package com.example.tamaskozmer.kotlinrxexample.presentation.view

import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.DetailsViewModel

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
interface DetailView {
    fun showDetails(details: DetailsViewModel)
    fun showError(error: String)
    fun showLoading()
    fun hideLoading()
}