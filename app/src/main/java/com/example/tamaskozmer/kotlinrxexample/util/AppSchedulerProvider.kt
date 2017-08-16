package com.example.tamaskozmer.kotlinrxexample.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Tamas_Kozmer on 8/4/2017.
 */
class AppSchedulerProvider : SchedulerProvider {
    override fun ioScheduler() = Schedulers.io()

    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}