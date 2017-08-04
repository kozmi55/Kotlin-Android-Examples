package com.example.tamaskozmer.kotlinrxexample.util

import io.reactivex.Scheduler

/**
 * Created by Tamas_Kozmer on 8/4/2017.
 */
interface SchedulerProvider {
    fun uiScheduler() : Scheduler
    fun ioScheduler() : Scheduler
}