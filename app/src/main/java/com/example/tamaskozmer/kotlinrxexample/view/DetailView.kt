package com.example.tamaskozmer.kotlinrxexample.view

import com.example.tamaskozmer.kotlinrxexample.model.entities.DetailsModel

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
interface DetailView {
    fun showDetails(detailsModel: DetailsModel)
    fun showError()
    fun showLoading()
    fun hideLoading()
}