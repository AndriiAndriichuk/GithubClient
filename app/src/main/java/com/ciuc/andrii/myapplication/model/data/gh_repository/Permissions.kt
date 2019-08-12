package com.ciuc.andrii.myapplication.model.data.gh_repository

import com.google.gson.annotations.SerializedName

class Permissions(
    @SerializedName("admin") var admin: Boolean = false,
    @SerializedName("push") var push: Boolean = false,
    @SerializedName("pull") var pull: Boolean = false
)