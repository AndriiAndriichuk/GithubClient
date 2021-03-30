package com.ciuc.andrii.myapplication.mappers

import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.model.User

object UserMapper {
    fun toAudio(userSearchItem: UserSearchItem): User {
        return User(
            userSearchItem.login,
            userSearchItem.html_url,
            userSearchItem.avatar_url
        )
    }
}