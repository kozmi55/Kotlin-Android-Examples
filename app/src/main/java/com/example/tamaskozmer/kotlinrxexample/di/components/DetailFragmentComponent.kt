package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailFragmentModule
import com.example.tamaskozmer.kotlinrxexample.presentation.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.view.fragments.DetailsFragment
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(DetailFragmentModule::class))
interface DetailFragmentComponent {
    fun inject(fragment: DetailsFragment)
    fun presenter() : DetailPresenter
}