package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

import com.example.tamaskozmer.kotlinrxexample.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
data class Heading(val title: String) : ViewType {
    override fun getViewType() = AdapterConstants.HEADING
}