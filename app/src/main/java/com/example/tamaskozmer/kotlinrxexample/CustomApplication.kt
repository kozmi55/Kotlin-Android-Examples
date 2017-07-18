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

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        component.inject(this)
    }
}