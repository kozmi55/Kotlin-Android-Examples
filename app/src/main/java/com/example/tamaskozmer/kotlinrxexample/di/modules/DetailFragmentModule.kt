package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Module
class DetailFragmentModule() {
    @Provides
    fun provideGetDetails(detailsRepository: DetailsRepository) = GetDetails(detailsRepository)

    @Provides
    fun providePresenter(getDetails: GetDetails, schedulerProvider: SchedulerProvider)
            = DetailPresenter(getDetails, schedulerProvider)
}