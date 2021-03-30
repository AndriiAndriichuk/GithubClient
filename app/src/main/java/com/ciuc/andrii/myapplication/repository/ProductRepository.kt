package com.ciuc.andrii.myapplication.repository

import com.ciuc.andrii.myapplication.client.models.followers.Subscriber
import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import io.reactivex.Single

interface ProductRepository {
    fun getRepositories(organization: String): Single<List<RepositoryDTO>>
    fun getSubscribers(userName: String): Single<List<Subscriber>>
}