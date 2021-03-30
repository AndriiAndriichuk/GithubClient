package com.ciuc.andrii.utils

import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO

class Constants {
    companion object {
        var listRepositories: List<RepositoryDTO> = arrayListOf()
        var repositoryDTO: RepositoryDTO? = null
        const val BASE_URL = "https://api.github.com"
    }
}