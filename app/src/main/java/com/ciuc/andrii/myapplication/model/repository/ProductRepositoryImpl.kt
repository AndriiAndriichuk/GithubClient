package com.ciuc.andrii.myapplication.model.repository

import com.ciuc.andrii.myapplication.model.retrofit.RetrofitInstance
import com.ciuc.andrii.myapplication.model.retrofit.RetrofitAPI
import com.ciuc.andrii.myapplication.model.data.followers.Subscriber
import com.ciuc.andrii.myapplication.model.data.gh_repository.RepositoryDTO
import io.reactivex.Single

class ProductRepositoryImpl : ProductRepository {

    override fun getRepositories(userName: String): Single<List<RepositoryDTO>> {
        return RetrofitInstance.getRetrofit()
            .create(RetrofitAPI::class.java)
            .getRepositories(userName)
    }

    override fun getSubscribers(userName: String): Single<List<Subscriber>> {
        return RetrofitInstance.getRetrofit()
            .create(RetrofitAPI::class.java)
            .getFollowers(userName)
    }

}