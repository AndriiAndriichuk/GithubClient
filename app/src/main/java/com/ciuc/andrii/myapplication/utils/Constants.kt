package com.ciuc.andrii.myapplication.utils

import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem

class Constants {
    companion object {
        var listRepositories: List<UserSearchItem> = arrayListOf()
        const val BASE_URL = "https://api.github.com"
    }
}