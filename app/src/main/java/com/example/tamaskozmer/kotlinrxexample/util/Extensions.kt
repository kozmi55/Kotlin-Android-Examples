package com.example.tamaskozmer.kotlinrxexample.util

import android.app.Activity
import android.os.Build
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tamaskozmer.kotlinrxexample.CustomApplication

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun isLollipopOrAbove(func: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        func()
    }
}

val Activity.customApplication: CustomApplication
    get() = application as CustomApplication

val Fragment.customApplication: CustomApplication
    get() = activity?.application as CustomApplication