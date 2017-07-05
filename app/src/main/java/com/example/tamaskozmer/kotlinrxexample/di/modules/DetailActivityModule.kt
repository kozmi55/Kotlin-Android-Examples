package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.view.activities.DetailActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Module
class DetailActivityModule(val activity: DetailActivity) {
    @Provides
    @Singleton
    fun providePresenter(userRepository: UserRepository) = DetailPresenter(userRepository)
}