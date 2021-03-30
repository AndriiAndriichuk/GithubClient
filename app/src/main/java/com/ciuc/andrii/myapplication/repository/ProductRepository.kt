package com.ciuc.andrii.myapplication.repository

import com.ciuc.andrii.myapplication.client.models.repository.RepositoryD
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.client.models.user.UserSearchResponse
import io.reactivex.Single

interface ProductRepository {
    fun getRepositories(organization: String): Single<List<RepositoryD>>
    fun searchUsers(query: String): Single<UserSearchResponse>
    fun searchUsersWithoutQuery(): Single<List<UserSearchItem>>
}