package com.example.tamaskozmer.kotlinrxexample.testutil

import android.support.test.InstrumentationRegistry
import com.example.tamaskozmer.kotlinrxexample.mocks.di.components.DaggerTestApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.mocks.di.modules.TestApplicationModule

class TestInjector(private val testApplicationModule: TestApplicationModule) {

    fun inject() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TestApplication

        DaggerTestApplicationComponent
                .builder()
                .appModule(testApplicationModule)
                .create(app)
                .inject(app)
    }
}