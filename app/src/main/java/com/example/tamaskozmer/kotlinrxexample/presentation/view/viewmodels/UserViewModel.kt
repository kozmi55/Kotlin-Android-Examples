package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType

data class UserViewModel(
    val userId: Long,
    val displayName: String,
    val reputation: Long,
    val profileImage: String
) : Parcelable, ViewType {

    override fun getViewType(): Int = AdapterConstants.USER_DETAILS

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UserViewModel> = object : Parcelable.Creator<UserViewModel> {
            override fun createFromParcel(source: Parcel): UserViewModel = UserViewModel(source)
            override fun newArray(size: Int): Array<UserViewModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readLong(),
    source.readString(),
    source.readLong(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(userId)
        dest.writeString(displayName)
        dest.writeLong(reputation)
        dest.writeString(profileImage)
    }
}