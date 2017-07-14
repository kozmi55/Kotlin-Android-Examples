package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.view.fragments.DetailsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Module
class DetailFragmentModule(val fragment: DetailsFragment) {
    @Provides
    @Singleton
    fun providePresenter(userRepository: UserRepository) = DetailPresenter(userRepository)
}