package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailActivityModule
import com.example.tamaskozmer.kotlinrxexample.presentation.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.view.activities.DetailActivity
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(DetailActivityModule::class))
interface DetailActivityComponent {
    fun inject(activity: DetailActivity)
    fun presenter() : DetailPresenter
}