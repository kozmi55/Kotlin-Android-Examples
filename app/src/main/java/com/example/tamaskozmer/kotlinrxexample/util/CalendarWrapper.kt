package com.example.tamaskozmer.kotlinrxexample.util

import java.util.Calendar

class CalendarWrapper {

    fun getCurrentTimeInMillis() = Calendar.getInstance().timeInMillis
}