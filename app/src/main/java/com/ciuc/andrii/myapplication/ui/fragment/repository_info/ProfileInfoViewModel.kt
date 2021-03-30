package com.ciuc.andrii.myapplication.ui.fragment.repository_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import com.ciuc.andrii.myapplication.repository.ProductRepositoryImpl
import com.ciuc.andrii.myapplication.repository.SharedStorage
import com.ciuc.andrii.myapplication.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProfileInfoViewModel(private var dataRepository: ProductRepositoryImpl,
                           private val sharedStorage: SharedStorage
) : ViewModel() {

    var repositoriesLiveData = SingleLiveEvent<List<RepositoryDTO>>()

    fun getRepositories(userName: String): LiveData<List<RepositoryDTO>> {
        val result: Single<List<RepositoryDTO>> = dataRepository.getRepositories(userName)

        result.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.d("subscribers -> %s", result.toString())
                repositoriesLiveData.value = result
            }, { error ->
                Timber.d("subscribers error -> %s", error)
            })
        return repositoriesLiveData
    }

}