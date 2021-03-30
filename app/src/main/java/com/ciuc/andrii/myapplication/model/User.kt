package com.ciuc.andrii.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val url: String,
    val avatar: String
): Parcelable