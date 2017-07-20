package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailFragmentModule
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.DetailPresenter
import dagger.Subcomponent

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Subcomponent(modules = arrayOf(DetailFragmentModule::class))
interface DetailFragmentComponent {
    fun presenter() : DetailPresenter
}