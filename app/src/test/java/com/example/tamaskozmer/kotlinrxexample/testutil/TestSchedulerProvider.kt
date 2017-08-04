package com.example.tamaskozmer.kotlinrxexample.testutil

import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import io.reactivex.schedulers.TestScheduler

/**
 * Created by Tamas_Kozmer on 8/4/2017.
 */
class TestSchedulerProvider(val testScheduler: TestScheduler) : SchedulerProvider {

    override fun uiScheduler() = testScheduler

    override fun ioScheduler() = testScheduler
}