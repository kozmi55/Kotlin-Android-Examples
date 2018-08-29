package com.example.tamaskozmer.kotlinrxexample.testutil

import com.example.tamaskozmer.kotlinrxexample.mocks.di.components.DaggerMockApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class TestApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMockApplicationComponent.builder().create(this)
    }
}