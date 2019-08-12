package com.ciuc.andrii.myapplication.model.retrofit

import com.ciuc.andrii.myapplication.model.data.followers.Subscriber
import com.ciuc.andrii.myapplication.model.data.gh_repository.RepositoryDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {
    @GET("/users/{user}/repos")
    fun getRepositories(@Path("user") userName: String): Single<List<RepositoryDTO>>

    @GET("https://api.github.com/users/{user}/followers")
    fun getFollowers(@Path("user") userName: String): Single<List<Subscriber>>
}