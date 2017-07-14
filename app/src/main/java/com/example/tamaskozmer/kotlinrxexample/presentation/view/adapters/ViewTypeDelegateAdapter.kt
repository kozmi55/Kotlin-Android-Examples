package com.example.tamaskozmer.kotlinrxexample.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}
