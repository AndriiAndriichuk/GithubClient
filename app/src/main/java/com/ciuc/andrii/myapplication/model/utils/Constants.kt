package com.ciuc.andrii.myapplication.model.utils

import com.ciuc.andrii.myapplication.model.data.gh_repository.RepositoryDTO

class Constants {
    companion object {
        var listRepositories: List<RepositoryDTO> = arrayListOf()
        var repositoryDTO: RepositoryDTO? = null
        const val BASE_URL = "https://api.github.com"
    }
}