package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
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
    fun provideGetDetails(detailsRepository: DetailsRepository) = GetDetails(detailsRepository)

    @Provides
    @Singleton
    fun providePresenter(getDetails: GetDetails) = DetailPresenter(getDetails)
}