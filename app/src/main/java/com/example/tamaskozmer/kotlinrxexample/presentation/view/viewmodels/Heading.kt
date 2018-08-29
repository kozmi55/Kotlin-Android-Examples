package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType

data class Heading(val title: String) : ViewType {
    override fun getViewType() = AdapterConstants.HEADING
}