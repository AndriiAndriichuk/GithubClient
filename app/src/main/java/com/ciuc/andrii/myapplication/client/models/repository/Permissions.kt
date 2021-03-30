package com.ciuc.andrii.myapplication.client.models.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Permissions(
    @SerializedName("admin") var admin: Boolean = false,
    @SerializedName("push") var push: Boolean = false,
    @SerializedName("pull") var pull: Boolean = false
): Parcelable