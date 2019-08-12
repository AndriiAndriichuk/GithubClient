package com.ciuc.andrii.myapplication.model.repository

import com.ciuc.andrii.myapplication.model.data.followers.Subscriber
import com.ciuc.andrii.myapplication.model.data.gh_repository.RepositoryDTO
import io.reactivex.Single

interface ProductRepository {
    fun getRepositories(organization: String): Single<List<RepositoryDTO>>
    fun getSubscribers(userName: String): Single<List<Subscriber>>
}