package com.ciuc.andrii.myapplication.client.models.gh_repository

import com.google.gson.annotations.SerializedName

class Permissions(
    @SerializedName("admin") var admin: Boolean = false,
    @SerializedName("push") var push: Boolean = false,
    @SerializedName("pull") var pull: Boolean = false
)