package com.example.tamaskozmer.kotlinrxexample.model.entities

import com.example.tamaskozmer.kotlinrxexample.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
data class Heading(val title: String) : ViewType {
    override fun getViewType() = AdapterConstants.HEADING
}