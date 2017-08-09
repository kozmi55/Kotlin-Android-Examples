package com.example.tamaskozmer.kotlinrxexample

import android.app.Application
import com.example.tamaskozmer.kotlinrxexample.di.components.ApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.di.components.DaggerApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.di.modules.ApplicationModule
import com.facebook.stetho.Stetho

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class CustomApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initAppComponent()

        Stetho.initializeWithDefaults(this);
        component.inject(this)
    }

    private fun initAppComponent() {
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}