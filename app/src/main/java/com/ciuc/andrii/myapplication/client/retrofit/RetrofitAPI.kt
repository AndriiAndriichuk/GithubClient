package com.ciuc.andrii.myapplication.client.retrofit

import com.ciuc.andrii.myapplication.client.models.followers.Subscriber
import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/users/{user}/repos")
    fun getRepositories(@Path("user") userName: String): Single<List<RepositoryDTO>>

    @GET("https://api.github.com/users/{user}/followers")
    fun getFollowers(@Query("user") userName: String): Single<List<Subscriber>>
}