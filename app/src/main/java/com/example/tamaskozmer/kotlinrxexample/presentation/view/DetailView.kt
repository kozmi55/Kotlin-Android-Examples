package com.example.tamaskozmer.kotlinrxexample.presentation.view

import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.DetailsViewData

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
interface DetailView {
    fun showDetails(details: DetailsViewData)
    fun showError(error: String)
    fun showLoading()
    fun hideLoading()
}