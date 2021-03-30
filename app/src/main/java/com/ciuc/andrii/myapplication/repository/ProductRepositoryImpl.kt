package com.ciuc.andrii.myapplication.repository

import com.ciuc.andrii.myapplication.client.models.repository.RepositoryD
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.client.models.user.UserSearchResponse
import com.ciuc.andrii.myapplication.client.retrofit.RetrofitAPI
import com.ciuc.andrii.myapplication.client.retrofit.RetrofitInstance
import io.reactivex.Single

class ProductRepositoryImpl : ProductRepository {

    override fun getRepositories(userName: String): Single<List<RepositoryD>> {
        return RetrofitInstance.getRetrofit()
            .create(RetrofitAPI::class.java)
            .getRepositories(userName)
    }

    override fun searchUsers(query: String): Single<UserSearchResponse> {
        return RetrofitInstance.getRetrofit()
            .create(RetrofitAPI::class.java)
            .searchUsers(query)
    }

    override fun searchUsersWithoutQuery(): Single<List<UserSearchItem>> {
        return RetrofitInstance.getRetrofit()
            .create(RetrofitAPI::class.java)
            .searchUsersWithoutQuery()
    }

}