package com.ciuc.andrii.myapplication.client.retrofit

import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.client.models.user.UserSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/users/{user}/repos")
    fun getRepositories(@Path("user") userName: String): Single<List<RepositoryDTO>>

    @GET("/search/users")
    fun searchUsers(@Query("q") query: String, @Query("per_page") perPage: Int = 50): Single<UserSearchResponse>

    @GET("/users")
    fun searchUsersWithoutQuery(@Query("per_page") perPage: Int = 50): Single<List<UserSearchItem>>
}