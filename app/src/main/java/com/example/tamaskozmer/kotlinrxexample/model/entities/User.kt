package com.example.tamaskozmer.kotlinrxexample.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
data class User(
        @SerializedName("user_id") val userId: Long,
        @SerializedName("display_name") val displayName: String,
        @SerializedName("reputation") val reputation: Long,
        @SerializedName("profile_image") val profileImage: String) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
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